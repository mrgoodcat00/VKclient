package com.goodcat.vkclient.application.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.MusicAdapter;
import com.goodcat.vkclient.application.model.music.MusicModel;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        ListView musicList = (ListView) findViewById(R.id.music_songs_list);

        List<MusicModel> musicModelObject = new ArrayList<MusicModel>();

        for(int i = 0; i<13;i++){
            MusicModel mMM = new MusicModel();
            mMM.setSongTitle("Summertime");
            mMM.setSongSinger("Skrillex");
            musicModelObject.add(mMM);
        }


        MusicAdapter adapter = new MusicAdapter(this,musicModelObject);

        musicList.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.main_header_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
