package com.hokagelab.www.cataloguemovie.scheduler;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hokagelab.www.cataloguemovie.R;
import com.hokagelab.www.cataloguemovie.SettingActivity;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.model.MovieDetail;
import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.Result;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";


    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";

    public static final String EXTRA_TYPE = "type";
    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;

    String message;


    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;

        getMovieDays(context, notifId);

    }

    private void getMovieDays(final Context context, int notifId){
        //        Load data
        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposNowPlaying(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Result> MovieList = Objects.requireNonNull(response.body()).getResults();
                Log.d(TAG, "Jumlah data Movie: " +MovieList);

                int index = new Random().nextInt(MovieList.size() - 1);

                String title = MovieList.get(index).getTitle();
                String message = MovieList.get(index).getOverview();
                String poster = MovieList.get(index).getPoster_path();

                int notifId = 200;
                showAlarmNotification(context,title, message, notifId, poster);
                Log.e(TAG, "Judul Film : "+title+"Overview : "+message+"gambar : "+poster );
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        //Load data
    }

    private void showAlarmNotification(final Context context, final String title, final String message, final int notifId, final String poster){
        final NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        getMovieDays(context);

        String imgPath = "http://image.tmdb.org/t/p/w780"+poster;

        Glide.with(context)
                .load(imgPath)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chanelId")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setLargeIcon(resource)
                                .setContentTitle("(Movie Day)\n"+title)
                                .setContentText(message)
                                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                .setSound(alarmSound);
                        notificationManagerCompat.notify(notifId, builder.build());
                    }

                });
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0])); //HOUR untuk 24 jam
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, 0);  //am 0 //pm 1 //jika HOUR hapus aja atau komentarin ini

        int requestCode = NOTIF_ID_ONETIME;
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Notification Release On", Toast.LENGTH_SHORT).show();

    }



    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Notification Off", Toast.LENGTH_SHORT).show();
    }

}