package com.example.sqli.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start , stop ;
    EditText hTime , mTime , jFreq;
    int seconds;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Calendar calendar = Calendar.getInstance() ;
    AlertDialog.Builder builder ;
    PendingIntent pendingIntent;


    NotificationCompat.Builder mBuilder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(this);
        hTime = (EditText) findViewById(R.id.hTime);
        mTime = (EditText) findViewById(R.id.mTime);
        jFreq = (EditText) findViewById(R.id.jFreq);
        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        seconds = Integer.parseInt((jFreq.getText()).toString())*24*3600;
//        mBuilder = new NotificationCompat.Builder(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.start){
            scheduleNotification(getNotification("tu dois prendre ton medicament aujourd'hui"), 5000);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                builder = new AlertDialog.Builder(this);//,android.R.style.Theme_Holo_Light_Dialog
            }else{
                builder = new AlertDialog.Builder(this);
            }

            builder.setTitle("Debut du service");
            builder.setMessage("le rappel de médicaments sera effectué " +
                    "chaque " + jFreq.getText().toString() + " jour(s) à " + hTime.getText().toString() +
                    "h " + mTime.getText().toString() + "min").show();
        }
        if(view.getId() == R.id.stop){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                builder = new AlertDialog.Builder(this);
            }else{
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Arrêt du service").setMessage("Service arrêté").show();
            if(alarmMgr != null && pendingIntent != null)
                alarmMgr.cancel(pendingIntent);

//            final AlertDialog a = builder.show();
//            try{
//                ((AppCompatTextView)a.findViewById(android.R.id.title)).setText("Arrêt du service");
//            }catch (Exception e){
//
//            }

            //stop the notications and kill the service
            // scheduleNotification(getNotification("tu dois prendre ton medicament aujourd'hui"), 5000);
        }
    }

    private void scheduleNotification(Notification notification, int delay) {


        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hTime.getText().toString()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mTime.getText().toString()));

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24 * Integer.parseInt(jFreq.getText().toString()), pendingIntent);
//)
    }

    private Notification getNotification(String content) {
             Notification.Builder builder = new Notification.Builder(this);
             Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             builder.setContentTitle("Scheduled Notification");
             builder.setContentText(content);
             builder.setSound(soundUri);
             builder.setVibrate(new long[]{1000,1000,1000});
             builder.setLights(Color.YELLOW,3000,3000);
             builder.setSmallIcon(R.drawable.oum);
             return builder.build();

    }


}
