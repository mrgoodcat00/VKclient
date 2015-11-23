package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{


    MediaPlayer mp;
    AudioManager am;
    String currentPlayingPosition = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mp = new MediaPlayer();
        mp.setOnCompletionListener(this);
        mp.setOnPreparedListener(this);
        Log.d("M_SERVICE","CREATED");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("M_SERVICE","Started with code:"+startId);
        return super.onStartCommand(intent, flags, startId);
    }



    private void startTrack(MediaPlayer mp, String fileUrl){
        try {
            mp.setDataSource(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.prepareAsync();
    }



    @Override
    public IBinder onBind(Intent intent) {

        String startCommand = intent.getStringExtra("START_COMMAND");
        Log.d("M_SERVICE","Current Command is - "+startCommand);
        if(startCommand.equals("play")) {
            String startPosition = intent.getStringExtra("START_POSITION");
            String fileUrl = intent.getStringExtra("FILE_URL");

                if(currentPlayingPosition == null){
                    startTrack(mp,fileUrl);
                    currentPlayingPosition = startPosition;
                } else {
                    if(!currentPlayingPosition.equals(startPosition)) {
                        startTrack(mp,fileUrl);
                        currentPlayingPosition = startPosition;
                    }
                }
        } else if(startCommand.equals("stop")){
            mp.stop();
            mp.release();
            stopSelf();
        } else if(startCommand.equals("pause")){
            mp.pause();
            Log.d("M_SERVICE","Stopped_"+intent.getDataString());
        }



        Log.d("M_SERVICE","Binded_"+intent.getDataString());
        return null;
    }


    @Override
    public void onDestroy() {
        mp.release();
        Log.d("M_SERVICE","service stopped");
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.release();
        mp = null;
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
