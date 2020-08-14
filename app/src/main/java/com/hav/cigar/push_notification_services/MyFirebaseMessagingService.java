package com.hav.cigar.push_notification_services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hav.cigar.R;
import com.hav.cigar.options.NotificationActivity;
import com.hav.cigar.startup.FrescoApplication;
import com.hav.cigar.utility.Constant;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
      FrescoApplication frescoApplication;
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";
        NotificationChannel mChannel;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String idChannel = "my_channel_01";
        Intent mainIntent;

        mainIntent = new Intent(this, NotificationActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, 0);

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel = null;
        // The id of the channel.

        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, idChannel);
        builder.setContentTitle(this.getString(R.string.app_name))
                .setSmallIcon(R.drawable.actdot)
                .setContentIntent(pendingIntent)
                .setContentText( "Cigar");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(idChannel, this.getString(R.string.app_name), importance);
            // Configure the notification channel.
            mChannel.setDescription(this.getString(R.string.alarm_notification));
            mChannel.enableLights(true);
            mChannel.setLightColor(getResources().getColor(R.color.colorAccent));
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            builder.setContentTitle(this.getString(R.string.app_name))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                    .setVibrate(new long[]{100, 250})
                    .setLights(Color.YELLOW, 500, 5000)
                    .setAutoCancel(true);
        }
        mNotificationManager.notify(1, builder.build());

//        Intent intent = new Intent(this, NotificationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        CharSequence name = getString(R.string.channel_name);
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//             mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//        }
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                + "://" + getPackageName() + "/raw/notif");
//        builder.setSound(alarmSound);
//        builder.setChannelId(CHANNEL_ID);
//        builder.setContentTitle("Cigar");
//        Map data = remoteMessage.getData();
//        String str = "";
//        if (data.get("message") != null) {
//            str = data.get("message").toString();
//        } else if (data.get("alert") != null) {
//            str = data.get("alert").toString();
//
//        }
//        builder.setContentText(str);
//        builder.setAutoCancel(true);
//        builder.setSmallIcon(R.drawable.actdot);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notifyID, builder.build());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationManager.createNotificationChannel(mChannel);
//        }
    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        frescoApplication=(FrescoApplication) getApplication();
        frescoApplication.getPreferences().setToken(Constant.PREFERENCE_DEVICE_TOKEN,s);
        Log.e("TOKEN", s);
    }

}
