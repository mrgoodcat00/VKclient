package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {

    MusicWorker worker = new MusicWorker();

    public class MusicWorker extends Binder implements MediaPlayer.OnCompletionListener,
                                                       MediaPlayer.OnPreparedListener,
                                                       MediaPlayer.OnBufferingUpdateListener{

        MediaPlayer mp;
        AudioManager am;

        boolean paused = false;
        boolean stoped = false;

        String currentPos = null;


        public MusicWorker(){
            this.mp = new MediaPlayer();
            this.mp.setOnPreparedListener(this);
            this.mp.setOnCompletionListener(this);
            this.mp.setOnBufferingUpdateListener(this);
        }


        public void playAudioTrack(String url,String pos){

            if(paused && !stoped && currentPos.equals(pos)) {
                mp.start();
            } else {
                try {
                    mp.reset();
                    mp.setDataSource(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.prepareAsync();
                paused = false;
                stoped = false;
                currentPos = pos;
            }
            Log.d("M_WORKER","incoming position:"+pos);
            Log.d("M_WORKER","previous position:"+currentPos);

        }

        public void pauseAudioTrack(String pos){
            if(mp != null && mp.isPlaying() && currentPos.equals(pos)) {
                mp.pause();
                paused = true;
            }
        }

        public void stopAudioTrack(String pos){
            if(mp != null && currentPos.equals(pos)) {
                mp.stop();
                stoped = true;
            }
        }

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp != null) {
                try {
                    mp.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("M_SERVICE","Service binded");
        return worker;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("M_SERVICE","Service unbinded");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    /*




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String startCommand = intent.getStringExtra("START_COMMAND");
        Log.d("M_SERVICE","Current Command is - "+startCommand);
        if(startCommand.equals("play")) {
            String startPosition = intent.getStringExtra("START_POSITION");
            String fileUrl = intent.getStringExtra("FILE_URL");
            if(mp == null){ Log.d("M_SERVICE","madeiaplayer is null");}
            startTrack(mp,fileUrl);
            currentPlayingPosition = startPosition;
        }*/



        /*else if(startCommand.equals("stop")){
            mp.stop();
            mp.release();
            stopSelf();
        } else if(startCommand.equals("pause")){
            mp.pause();
            Log.d("M_SERVICE","paused_"+intent.getDataString());
        }*/



/*
        Log.d("M_SERVICE","Started with code:"+startId);
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        Log.d("M_SERVICE","Binded_"+intent.getDataString());
        return mBinder;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, final int percent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("M_SERVICE", ""+percent);
            }
        });
    }

    private void startTrack(MediaPlayer mp, String fileUrl){
        try {
            if(mp == null){ mp = new MediaPlayer();}
            mp.setDataSource(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.prepareAsync();
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

    @Override
    public void onDestroy() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
        }
        Log.d("M_SERVICE","service stopped");
        super.onDestroy();
    }*/
}
