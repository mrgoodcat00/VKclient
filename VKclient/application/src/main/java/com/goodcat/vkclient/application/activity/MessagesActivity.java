package com.goodcat.vkclient.application.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.MessagesAdapter;
import com.goodcat.vkclient.application.model.messages.MessagesModel;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Messages");



        ListView messagesList = (ListView) findViewById(R.id.user_messages);
        List<MessagesModel> mesModel = new ArrayList<MessagesModel>();

        for(int i = 0; i<17; i++) {
            mesModel.add(getMessage());
        }

        MessagesAdapter adapter = new MessagesAdapter(this,mesModel);
        messagesList.setAdapter(adapter);






        Button back = (Button) findViewById(R.id.main_header_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }







    private MessagesModel getMessage(){
        MessagesModel messagesModel = new MessagesModel();
        return messagesModel;
    }
}
