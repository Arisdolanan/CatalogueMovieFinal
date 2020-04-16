package com.hokagelab.www.cataloguemovie;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.scheduler.AlarmPreference;
import com.hokagelab.www.cataloguemovie.scheduler.AlarmReceiver;
import com.hokagelab.www.cataloguemovie.scheduler.AlarmReceiverRelease;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";
    private TextView tvRepeatingTime, tvOneTimeTime;

    private Calendar calOneTimeDate, calOneTimeTime , calRepeatTimeTime;
    private AlarmReceiver alarmReceiver;
    private AlarmReceiverRelease alarmReceiverRelease;
    private AlarmPreference alarmPreference;

    String dateYa;
    String message;

    Switch btn_daily, btn_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button setting_bahasa;

        btn_daily = findViewById(R.id.daily);
        btn_release = findViewById(R.id.release);
        setting_bahasa = findViewById(R.id.action_change_settings);

        btn_daily.setOnCheckedChangeListener(this);
        btn_release.setOnCheckedChangeListener(this);
        setting_bahasa.setOnClickListener(this);

        tvRepeatingTime = findViewById(R.id.tv_repeating_alarm_time);
        tvOneTimeTime = findViewById(R.id.tv_one_time);

        calRepeatTimeTime = Calendar.getInstance();
        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();
        alarmReceiverRelease = new AlarmReceiverRelease();

        if (!TextUtils.isEmpty(alarmPreference.getRepeatingTime())){
            setRepeatingText();
        }

        if (!TextUtils.isEmpty(alarmPreference.getOneTimeDate())){
            setOneTimeText();
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle("Setting");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        return true;
    }

    @Override
    public void onBackPressed()
    {
        onSupportNavigateUp();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.daily) {
            if (btn_daily.isChecked()){
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String repeatTimeTime = "07:00";
                alarmPreference.setRepeatingTime(repeatTimeTime);
                Log.e(TAG, "onClick: "+repeatTimeTime );
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                        alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());

            } else {
                alarmReceiver.cancelAlarm(SettingActivity.this, AlarmReceiver.TYPE_REPEATING);
//                onDestroy();
            }
        } else if (buttonView.getId() == R.id.release){
            if (btn_release.isChecked()) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String repeatTimeTime = "08:00";
                alarmPreference.setOneTimeTime(repeatTimeTime);
                Log.e(TAG, "onClick: "+repeatTimeTime );
                alarmReceiverRelease.setRepeat(this, AlarmReceiverRelease.TYPE_REPEATING,
                        alarmPreference.getOneTimeTime(), alarmPreference.getOneTimeMessage());

            } else {
                alarmReceiverRelease.cancelAlarm(SettingActivity.this, AlarmReceiverRelease.TYPE_ONE_TIME);
            }
        }
    }

    private void setRepeatingText() {
        tvRepeatingTime.setText(alarmPreference.getRepeatingTime());
    }

    private void setOneTimeText() {
        tvOneTimeTime.setText(alarmPreference.getOneTimeTime());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
    }
}
