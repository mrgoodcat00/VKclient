package com.goodcat.vkclient.application.service;

import android.app.Application;

public class VKclient extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadImageService.init(this);
    }
}
