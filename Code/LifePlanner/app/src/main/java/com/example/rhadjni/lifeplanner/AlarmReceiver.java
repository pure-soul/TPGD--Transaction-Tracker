package com.example.rhadjni.lifeplanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.rhadjni.lifeplanner.Activities.NotificationActivity;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationCompat;

@SuppressWarnings("ALL")
public class AlarmReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent){
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("Transaction Tracker")
                .setContentText("Remember to record your expenses")
                .setTicker("Record them now!!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);

    }


}
