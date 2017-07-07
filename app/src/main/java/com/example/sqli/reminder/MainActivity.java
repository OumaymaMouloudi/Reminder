package com.example.sqli.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start ;
    EditText hTime , mTime , jFreq;

    NotificationCompat.Builder mBuilder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        hTime = (EditText) findViewById(R.id.hTime);
        mTime = (EditText) findViewById(R.id.mTime);
        jFreq = (EditText) findViewById(R.id.jFreq);
        mBuilder = new NotificationCompat.Builder(this);
    }

    @Override
    public void onClick(View view) {
        scheduleNotification(getNotification("5 second delay"), 5000);
    }

    private void scheduleNotification(Notification notification, int delay) {


        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
             Notification.Builder builder = new Notification.Builder(this);
             builder.setContentTitle("Scheduled Notification");
             builder.setContentText(content);
             builder.setSmallIcon(R.drawable.ic_launcher);
             return builder.build();
    }


}
