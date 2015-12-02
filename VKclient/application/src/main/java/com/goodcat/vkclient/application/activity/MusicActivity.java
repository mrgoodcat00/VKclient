package com.goodcat.vkclient.application.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.MusicAdapter;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.service.MusicService;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.ResponseCallback;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends Activity{

    private SessionToken st;
    private String userID;

    private MusicService.MusicWorker musicBinder;

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

    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicService.MusicWorker) service;
            Log.d("M_ACTIVITY", "Service connected!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("M_ACTIVITY", "Service disconnected!");
        }
    };

    private void setAudios(List<MusicModel> items) {
        List<MusicModel> result = new ArrayList<MusicModel>();
        if(musicBinder.getCurrentPlayingTrack() != null && !items.isEmpty()) {
            for (MusicModel m : items) {
                if(m.getId() == musicBinder.getCurrentPlayingTrack().getId()){
                    m.setIsPlaying(true);
                }
                result.add(m);
            }
        } else { result = items;}
        ListView musicList = (ListView) findViewById(R.id.music_songs_list);
        MusicAdapter adapter = new MusicAdapter(this,result,musicBinder);
        musicList.setAdapter(adapter);

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("M_ACTIVITY","id:"+view.getId()+" pos:"+position);
            }
        });
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

        startService(new Intent(this, MusicService.class));
        bindService(new Intent(this,MusicService.class),musicServiceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LOGGER","App is stopped!");
        unbindService(connection);
        unbindService(musicServiceConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
