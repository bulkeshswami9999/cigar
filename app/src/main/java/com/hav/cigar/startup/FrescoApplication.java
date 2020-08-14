package com.hav.cigar.startup;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.hav.cigar.cache.ImagePipelineConfigFactory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hav.cigar.preferences.PrefManager;

/**
 * Created by 06peng on 2015/6/24.
 */
public class FrescoApplication extends Application {
    private PrefManager preferences;;
    public static final String CHANNEL_1_ID= "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
        doInit();
        //createNotificationChanne();
    }

    private void doInit() {
        this.preferences = new PrefManager(this);
    }

    public synchronized PrefManager getPreferences() {
        return preferences;
    }

//    // Function for Notification Channel
//    private void createNotificationChanne() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name;
//            NotificationChannel channel = new NotificationChannel(
//                    CHANNEL_1_ID,
//                    "Payment Response",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            channel.setDescription("This is for Payment Response ");
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//
//    } // - end of createNoftificationChannel func

}
