package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;
import com.goodcat.vkclient.application.model.music.MusicModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MusicService extends Service {

    MusicWorker worker;

    public class MusicWorker extends Binder implements MediaPlayer.OnCompletionListener,
                                                       MediaPlayer.OnPreparedListener,
                                                       MediaPlayer.OnBufferingUpdateListener,
                                                       MediaPlayer.OnErrorListener {
        private MediaPlayer mp;
        private boolean paused = false;
        private boolean stoped = false;
        private int lastPosition = -1;
        private MusicModel currentPlayingItem = null;


        private SeekBar progressBar;

        public MusicWorker(Context cntxt){
            if(mp == null) {
                this.mp = new MediaPlayer();
                this.mp.setOnPreparedListener(this);
                this.mp.setOnCompletionListener(this);
                this.mp.setOnBufferingUpdateListener(this);
                this.mp.setOnErrorListener(this);
            }
        }

        public MusicModel getCurrentPlayingTrack(){
            return currentPlayingItem;
        }
        public int getLastPosition(){return lastPosition;}
        public void setCurrentPlayingTrack(MusicModel track){
            currentPlayingItem = track;
        }

        public int playAudioTrack(String url, int pos) {
            lastPosition = pos;
            if (paused && !stoped && lastPosition == pos && mp != null) {
                mp.start();
                return -1;
            } else {
                try {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1500);
                        mp.reset();
                        mp.setDataSource(url);
                        mp.prepareAsync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    mp = new MediaPlayer();
                    mp.setOnPreparedListener(this);
                    mp.setOnCompletionListener(this);
                    mp.setOnBufferingUpdateListener(this);
                    try {
                        mp.setDataSource(url);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    mp.prepareAsync();
                }

                return lastPosition;
            }
        }

        public void pauseAudioTrack(int pos){
            if(mp != null && mp.isPlaying() && lastPosition == pos) {
                mp.pause();
                paused = true;
            }
        }

        public int stopAudioTrack(int pos){
            if(mp != null && lastPosition == pos && !stoped) {
                mp.stop();
                stoped = true;
                paused = false;
                return lastPosition;
            }
            return -1;
        }

        public void setProgressBar(SeekBar progressBarIn){
            if(progressBarIn != null){
                progressBar = progressBarIn;
            }
        }

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if(mp != null && (mp.isPlaying() || paused)) {
                progressBar.setMax(mp.getDuration()/ 1000);
                Log.d("M_SERVICE", "track percents " + percent + "||" + mp.getCurrentPosition() / 1000L);
                progressBar.setProgress((int) (mp.getCurrentPosition() / 1000L));
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp != null) {
                try {
                    mp.release();
                    currentPlayingItem=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            paused = false;
            stoped = false;
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.e(getPackageName(), String.format("Error(%s%s)", what, extra));
            mp.release();
            return true;
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
