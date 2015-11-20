package com.goodcat.vkclient.application.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.MusicAdapter;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.ResponseCallback;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.List;

public class MusicActivity extends Activity{

    private SessionToken st;
    private String userID;
    private MediaPlayer mediaPlayer;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RequestService.RequestWorker worker = (RequestService.RequestWorker) service;
            worker.getUserAudio(new ResponseCallback<MusicModel>() {
                @Override
                public void onResponse(List<MusicModel> items) {
                    setAudios(items);
                }
            },userID);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void setAudios(List<MusicModel> items) {
        ListView musicList = (ListView) findViewById(R.id.music_songs_list);
        MusicAdapter adapter = new MusicAdapter(this,items);
        musicList.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        st = Session.getSession(this);
        userID = getIntent().getStringExtra("userId");

        Button back = (Button) findViewById(R.id.main_header_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGER", "App is started!");
        bindService(new Intent(this,RequestService.class),connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LOGGER","App is stopped!");
        unbindService(connection);
        //releaseMP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
