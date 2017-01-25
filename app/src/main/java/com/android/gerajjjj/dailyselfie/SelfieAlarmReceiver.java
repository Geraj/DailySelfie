package com.android.gerajjjj.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Gerajjjj on 1/24/2017.
 */
public class SelfieAlarmReceiver extends BroadcastReceiver {
    /**
     * Constructor
     */
    public SelfieAlarmReceiver() {
    }
    private final long[] mVibratePattern = { 0, 200, 200, 300 };
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String SELFIE = "Selfie time";
    private static final String SELFIE2 = "Time to take a selfie";
    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // The Intent to be used when the user clicks on the Notification View
        Intent mNotificationIntent = new Intent(context, MainActivity.class);

        // The PendingIntent that wraps the underlying Intent
        PendingIntent mContentIntent = PendingIntent.getActivity(context, 0,
                mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Build the Notification
        Notification.Builder notificationBuilder = new Notification.Builder(
                context)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setAutoCancel(true).setContentTitle(SELFIE)
                .setContentText(SELFIE2).setContentIntent(mContentIntent)
                .setVibrate(mVibratePattern);
        // Get the NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Pass the Notification to the NotificationManager:
        mNotificationManager.notify(MY_NOTIFICATION_ID,
                notificationBuilder.build());
    }
}
