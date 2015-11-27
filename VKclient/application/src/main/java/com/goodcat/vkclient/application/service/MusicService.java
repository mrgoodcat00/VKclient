package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;

public class MusicService extends Service {

    MusicWorker worker;

    public class MusicWorker extends Binder implements MediaPlayer.OnCompletionListener,
                                                       MediaPlayer.OnPreparedListener,
                                                       MediaPlayer.OnBufferingUpdateListener{
        MediaPlayer mp;
        boolean paused = false;
        boolean stoped = false;
        int previousTrack = -1;
        int progress = 0;
        SeekBar progressBar = null;
        Context context;

        public MusicWorker(Context cntxt){
            if(mp == null) {
                this.context = cntxt;
                this.mp = new MediaPlayer();
                this.mp.setOnPreparedListener(this);
                this.mp.setOnCompletionListener(this);
                this.mp.setOnBufferingUpdateListener(this);
            }

        }

        public int getCurrentTrack(){
            return previousTrack;
        }

        public MediaPlayer getMediaPlayer(){
            return mp;
        }

        public int playAudioTrack(String url,int pos){
            //if(mp.isPlaying()){mp.stop(); mp.reset();}

            if(paused && !stoped && previousTrack == pos && mp != null) {
                mp.start();
                return -1;
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
                previousTrack = pos;
                return previousTrack;
            }
        }

        public void pauseAudioTrack(int pos){
            if(mp != null && mp.isPlaying() && previousTrack == pos) {
                mp.pause();
                paused = true;
            }
        }

        public int stopAudioTrack(int pos){
            if(mp != null && previousTrack == pos) {
                mp.stop();
                return previousTrack;
            }
            return -1;
        }

        public void setProgressBar(SeekBar progressBarIn){
            if(progressBarIn != null){
                progressBar = progressBarIn;
            } else {Log.d("M_SERVICE", "progress bar is null in setter");}
        }


        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if(mp != null && mp.isPlaying()) {
                progress = percent;
                Log.d("M_SERVICE", "track percents " + percent + "||" + mp.getCurrentPosition() / 1000L);
                progressBar.setProgress((int) (mp.getCurrentPosition() / 1000L));
            }
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
        if(worker == null){
            return new MusicWorker(this);
        } else {
            return worker;
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("M_SERVICE","Service destroyed");
    }

}
