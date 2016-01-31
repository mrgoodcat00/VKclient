package com.goodcat.vkclient.application.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.DialogMessageAdapter;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.service.MusicService;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.callbacks.ResponseMessagesWithUserData;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private SessionToken st;
    private SwipeRefreshLayout swipeLayout;
    private boolean messagesAreLoading = false;
    private RequestService.RequestWorker requestWorker;
    private MusicService.MusicWorker musicBinder;
    private int chatId = 0;
    private int fromId = 0;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SERVICE", "Connected");
            requestWorker = (RequestService.RequestWorker) service;
            requestWorker.getPrivateDialog(new ResponseMessagesWithUserData<MessagesModel, UserModel>() {
                @Override
                public void onResponse(List<MessagesModel> items, List<UserModel> userIds) {
                    setMessages(items, userIds);
                }
            }, 0, chatId, fromId);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SERVICE", "Disconnected");
        }
    };

    ServiceConnection musicServiceConnection = new ServiceConnection() {
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

    private void setMessages(List<MessagesModel> items, List<UserModel> userIds) {
        ListView messagesList = (ListView) findViewById(R.id.user_message);
        ArrayAdapter<MessagesModel> adapter = new DialogMessageAdapter(this,items,userIds,musicBinder);
        Log.d("users id ", "" + userIds.size());
        messagesList.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        st = Session.getSession(this);
        setContentView(R.layout.activity_message);

        Intent intent  =  getIntent();
        chatId = (int) (intent.getLongExtra("dialogId",0)+2000000000);
        fromId = (int) (intent.getLongExtra("fromId",0));

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View actionBarView = View.inflate(this, R.layout.old_custom_menu, null);
        TextView title = (TextView) actionBarView.findViewById(R.id.main_header_page_title);
        Button backButton = (Button) actionBarView.findViewById(R.id.main_header_back_button);
        actionBar.setCustomView(actionBarView);
        title.setVisibility(View.VISIBLE);
        title.setText("Message");
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGER", "MessagesActivity is started!");
        bindService(new Intent(MessageActivity.this,RequestService.class),serviceConnection,BIND_AUTO_CREATE);
        startService(new Intent(MessageActivity.this, MusicService.class));
        bindService(new Intent(MessageActivity.this,MusicService.class),musicServiceConnection,BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        unbindService(musicServiceConnection);
    }
}
