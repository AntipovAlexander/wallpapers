package com.antipov.mvp_template.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.antipov.mvp_template.R;

public class NotificationUtil {
    public static void notify(Context context, String title, String text){
        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Wallpapers";// The user-visible name of the channel.
        // Create a notification and set the notification channel.
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_report_problem_black_24dp)
                .setContentTitle(title)
                .setContentText(text);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder.setChannelId(CHANNEL_ID);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            if (notificationManager != null) notificationManager.createNotificationChannel(mChannel);
        }

        if (notificationManager != null) notificationManager.notify(notifyID, builder.build());
    }
}
