package com.goodcat.vkclient.application.service;

import android.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

public class VKclient extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadImageService.init(this);
        JodaTimeAndroid.init(this);
    }
}
