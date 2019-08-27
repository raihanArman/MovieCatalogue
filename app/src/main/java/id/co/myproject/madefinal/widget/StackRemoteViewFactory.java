package id.co.myproject.madefinal.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;

import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.CONTENT_URI_MOVIE;
import static id.co.myproject.madefinal.widget.FavoriteWidget.EXTRA_ITEM;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    int mAppWidgetId;
    private Cursor cursor;
    Movie movie;

    public StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(Uri.parse(CONTENT_URI_MOVIE+"/movie"), null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        final long token = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(Uri.parse(CONTENT_URI_MOVIE+"/movie"), null, null, null, null);
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.favorite_widget_item);
        if (cursor.moveToPosition(i)){
            movie = new Movie(cursor);
            Bitmap bitmap;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(BuildConfig.BASE_URL_IMG+movie.getPosterPath())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                rv.setImageViewBitmap(R.id.iv_fav_widget, bitmap);
                rv.setTextViewText(R.id.tv_fav_title, movie.getTitle());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        Bundle extras = new Bundle();
        extras.putInt(EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.iv_fav_widget,fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
