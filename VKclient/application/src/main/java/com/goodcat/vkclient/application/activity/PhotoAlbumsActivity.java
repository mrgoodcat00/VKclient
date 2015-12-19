package com.goodcat.vkclient.application.activity;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.PhotoAlbumsAdapter;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumsModel;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.callbacks.ResponseCallback;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.List;

public class PhotoAlbumsActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private SessionToken st;
    private RequestService.RequestWorker requestWorker;
    private boolean deviceHaveMenuButton = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SERVICE", "Connected");
            requestWorker = (RequestService.RequestWorker) service;
            requestWorker.getPhotoAlbums(new ResponseCallback<PhotoAlbumsModel>() {
                @Override
                public void onResponse(List<PhotoAlbumsModel> items) {
                    setAlbums(items);
                }
            },st.getUserId());
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SERVICE","Disconnected");
        }
    };

    private void setAlbums(List<PhotoAlbumsModel> items) {
        ListView albumsList = (ListView) findViewById(R.id.album_photo_list_albums);
        PhotoAlbumsAdapter adapter = new PhotoAlbumsAdapter(getBaseContext(),items);
        albumsList.setAdapter(adapter);
        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoAlbumsModel clickedAlbum = (PhotoAlbumsModel) parent.getItemAtPosition(position);
                Intent newIntent = new Intent(PhotoAlbumsActivity.this,PhotoAlbumActivity.class);
                newIntent.putExtra("album_name",clickedAlbum.getTitle());
                newIntent.putExtra("album_id",clickedAlbum.getId());
                newIntent.putExtra("album_photos_quantity",clickedAlbum.getSize());
                startActivity(newIntent);
            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        st = Session.getSession(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View actionBarView = View.inflate(this, R.layout.old_custom_menu, null);
        TextView title = (TextView) actionBarView.findViewById(R.id.main_header_page_title);
        Button backButton = (Button) actionBarView.findViewById(R.id.main_header_back_button);
        actionBar.setCustomView(actionBarView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        title.setVisibility(View.VISIBLE);
        title.setText("Photo Albums");
        backButton.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGER","PhotoAlbmsActivity is started!");
        if(Build.VERSION.SDK_INT <= 10){deviceHaveMenuButton = true;}
        bindService(new Intent(PhotoAlbumsActivity.this,RequestService.class),serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
