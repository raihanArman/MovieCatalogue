package id.co.myproject.madefinal.notif;

import androidx.appcompat.app.AppCompatActivity;
import id.co.myproject.madefinal.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    public final static String PREF_NAME = "reminderPreferences";
    public static final String TYPE_REMINDER_DAILY = "reminderAlarmDaily";
    public final static String KEY_REMINDER_DAILY = "DailyReminder";
    public static final String KEY_REMINDER_RELEASE = "upcomingReminder";
    public static final String TYPE_REMINDER_RELEASE = "reminderAlarmRelease";

    Switch swDaily, swRelease;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    DailyReceiver dailyReceiver;
    MovieReleaseReceiver movieReleaseReceiver;
    RelativeLayout btnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dailyReceiver = new DailyReceiver();
        movieReleaseReceiver = new MovieReleaseReceiver();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        swDaily = findViewById(R.id.sw_daily);
        swRelease = findViewById(R.id.sw_release);
        btnLanguage = findViewById(R.id.btn_bahasa);

        cekReminder();

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });

        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    editor.putBoolean(KEY_REMINDER_DAILY, true);
                    editor.commit();
                }else {
                    editor.putBoolean(KEY_REMINDER_DAILY, false);
                    editor.commit();
                }
            }
        });

        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    editor.putBoolean(KEY_REMINDER_RELEASE, true);
                    editor.commit();
                }else {
                    editor.putBoolean(KEY_REMINDER_RELEASE, false);
                    editor.commit();
                }
            }
        });
    }

    private void cekReminder(){
        boolean cekDaily = sharedPreferences.getBoolean(KEY_REMINDER_DAILY, false);
        boolean cekRelease = sharedPreferences.getBoolean(KEY_REMINDER_RELEASE, false);
        if (cekDaily){
            swDaily.setChecked(true);
            dailyReminderOn();
        }else {
            swDaily.setChecked(false);
            dailyReminderOff();
        }

        if (cekRelease){
            swRelease.setChecked(true);
            releaseReminderOn();
        }else {
            swRelease.setChecked(false);
            releaseRemiderOff();
        }
    }


    private void releaseReminderOn(){
        String time = "23:17";
        String message = "Release movie message";
        movieReleaseReceiver.setAlarm(this, TYPE_REMINDER_RELEASE, time, message);
    }

    private void releaseRemiderOff(){
        movieReleaseReceiver.cancelAlarm(this);
    }

    private void dailyReminderOn(){
        String time = "23:17";
        String message = "Daily message";
        dailyReceiver.setAlarm(this, TYPE_REMINDER_DAILY, time, message);
    }
    private void dailyReminderOff(){
        dailyReceiver.cancelAlarm(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
