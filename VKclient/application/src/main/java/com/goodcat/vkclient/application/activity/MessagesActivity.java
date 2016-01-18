package com.goodcat.vkclient.application.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.MessagesAdapter;
import com.goodcat.vkclient.application.model.messages.DialogModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.callbacks.ResponseMessagesWithUserData;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.LinkedHashMap;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private SessionToken st;
    private SwipeRefreshLayout swipeLayout;
    private boolean messagesAreLoading = false;
    private RequestService.RequestWorker requestWorker;
    private MessagesAdapter adapter;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SERVICE", "Connected");
            requestWorker = (RequestService.RequestWorker) service;
            requestWorker.getPrivateMessagesWithOfset(new ResponseMessagesWithUserData<DialogModel, UserModel>() {
                @Override
                public void onResponse(List<DialogModel> items, List<UserModel> userIds) {
                    setMessages(items, userIds);
                }
            }, 0);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SERVICE", "Disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        st = Session.getSession(this);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swap_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        unbindService(serviceConnection);
                        bindService(new Intent(MessagesActivity.this,RequestService.class),serviceConnection,BIND_AUTO_CREATE);
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View actionBarView = View.inflate(this, R.layout.old_custom_menu, null);
        TextView title = (TextView) actionBarView.findViewById(R.id.main_header_page_title);
        Button backButton = (Button) actionBarView.findViewById(R.id.main_header_back_button);
        actionBar.setCustomView(actionBarView);
        title.setVisibility(View.VISIBLE);
        title.setText("Messages");
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setMessages(final List<DialogModel> items, final List<UserModel> ids) {
        LinkedHashMap<String,UserModel> userMap= new LinkedHashMap<String, UserModel>();
        for(UserModel d:ids){
            userMap.put(d.getStringId(),d);
        }

        ListView messagesList = (ListView) findViewById(R.id.user_messages);
        adapter = new MessagesAdapter(this,items,userMap);
        messagesList.setAdapter(adapter);

        messagesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("setOnScrollListener", " firstVisibleItem:" + (firstVisibleItem+visibleItemCount));
                Log.d("setOnScrollListener", " totalItemCount:" + (totalItemCount) );
                if(totalItemCount == firstVisibleItem+visibleItemCount && !messagesAreLoading ) {
                    updateAdapter(totalItemCount,items,ids);
                }
            }
        });

    }

    private void updateAdapter(int i, List<DialogModel> dialogsList, List<UserModel> ids){
        messagesAreLoading = true;
        requestWorker.getPrivateMessagesWithOfset(new ResponseMessagesWithUserData<DialogModel, UserModel>() {
            @Override
            public void onResponse(List<DialogModel> dialogsList, List<UserModel> userIds) {
                adapter.updateMessagesList(dialogsList, userIds);
                messagesAreLoading = false;
            }
        }, i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGER","MessagesActivity is started!");
        bindService(new Intent(MessagesActivity.this,RequestService.class),serviceConnection,BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
