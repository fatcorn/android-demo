package com.den.demo.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.den.demo.LoginActivity;
import com.den.demo.R;

/**
 * 消息通知工具类
 */
public class NotificationUtil {

    private static Context context = LoginActivity.getContextOfApplication();

    private static final String CHANNEL_ID= "V";
    private static final String CHANNEL_NAME= "V";
    private static final String CHANNEL_DESCRIPTION= "V description";


    public static NotificationCompat.Builder sendNotification(String title, String content)
    {
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else
        {
            builder = new NotificationCompat.Builder(context);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //点击自动删除通知
        builder.setAutoCancel(true);
        return builder;
    }

    //创建通知渠道，API 26 +
    public static void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =  context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
