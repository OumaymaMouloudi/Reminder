package com.example.sqli.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sqli on 07/07/2017.
 */

public class NotificationPublisher extends BroadcastReceiver {


    private static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static boolean status ;

    public static boolean getStatus(){
        return status;
    }

    public static void setStatus(boolean s){
        status = s ;
    }

    public static String getNotificationId() {
        return NOTIFICATION_ID;
    }

    public static void setNotificationId(String notificationId) {
        NOTIFICATION_ID = notificationId;
    }


    public void onReceive(Context context, Intent intent) {


                 NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                 Notification notification = intent.getParcelableExtra(NOTIFICATION);
                 int id = intent.getIntExtra(getNotificationId(), 0);
                 notificationManager.notify(id, notification);

             }


}
