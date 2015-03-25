package com.prehistoricprograms.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    // Notification ID to allow for future updates
    private static final int MY_NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // The Intent to be used when the user clicks on the Notification View
        Intent mNotificationIntent = new Intent(context, MainActivity.class);

        // The PendingIntent that wraps the underlying Intent
        PendingIntent mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, PendingIntent.FLAG_ONE_SHOT);

        // Build the Notification
        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setTicker(context.getString(R.string.app_name) + " - " + context.getString(R.string.time_for_selfie))
                .setSmallIcon(R.drawable.ic_camera)
                .setAutoCancel(true)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.time_for_selfie))
                .setContentIntent(mContentIntent);

        // Get the NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Pass the Notification to the NotificationManager:
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
    }
}
