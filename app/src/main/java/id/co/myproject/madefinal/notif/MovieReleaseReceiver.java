package id.co.myproject.madefinal.notif;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.core.app.NotificationCompat;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.request.ApiRequest;
import id.co.myproject.madefinal.request.RetrofitRequest;
import id.co.myproject.madefinal.util.Language;
import id.co.myproject.madefinal.view.DetailActivity;
import retrofit2.Response;

import static id.co.myproject.madefinal.notif.DailyReceiver.CHANNEL_NAME;
import static id.co.myproject.madefinal.view.DetailActivity.EXTRAS_DETAIL_MOVIE;

public class MovieReleaseReceiver extends BroadcastReceiver implements LoadDataRelease{

    public final static int NOTIFICATION_ID = 502;
    public static String CHANNEL_ID = "channel_01";
    public static final String TAG = MovieReleaseReceiver.class.getSimpleName();
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";

    Movie movie;

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        new LoadMovieReleaseCallback(context, this).execute();
    }

    private void sendNotification(Context context,String title, String desc, int id){
        String message = desc;
        if (desc.isEmpty()){
            message = context.getResources().getString(R.string.release_reminder);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id_movie", movie.getId());
        intent.putExtra("detail", EXTRAS_DETAIL_MOVIE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000,1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if (notificationManager != null){
            notificationManager.notify(id, notification);
        }

    }

    public void setAlarm(Context context, String type, String time, String message){
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_RECIEVE, message);
        intent.putExtra(EXTRA_TYPE_RECIEVE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    @Override
    public void getMovieRelease(Movie movies) {
        sendNotification(context, movies.getTitle(), movies.getOverview(),NOTIFICATION_ID);
    }

    private class LoadMovieReleaseCallback extends AsyncTask<Void, Void, Movie>{
        private WeakReference<Context> weakContext;
        private WeakReference<LoadDataRelease> weakRelease;

        public LoadMovieReleaseCallback(Context context, LoadDataRelease callback){
            this.weakContext = new WeakReference<>(context);
            this.weakRelease = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakContext.get();
        }

        @Override
        protected Movie doInBackground(Void... voids) {
            List<Movie> movies = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            String now = dateFormat.format(date);
            ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
            try {
                Response<MovieResults> response = apiRequest.getReleaseMovie(BuildConfig.API_KEY, now, now, Language.getCountry()).execute();
                if (response.isSuccessful()){
                    movies = response.body().getMovieModels();
                    Log.d(TAG, "onResponse: list : "+movies.size());
                    int index = new Random().nextInt(movies.size());
                    movie = movies.get(index);
                    Log.d(TAG, "doInBackground: movie title"+movie.getTitle());
                    return movie;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movies) {
            super.onPostExecute(movies);
            weakRelease.get().getMovieRelease(movies);
        }
    }
}
