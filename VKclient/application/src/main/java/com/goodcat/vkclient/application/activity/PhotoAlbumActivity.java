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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.PhotoAlbumAdapter;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumModel;
import com.goodcat.vkclient.application.service.DownloadImageService;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.callbacks.ResponseCallback;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumActivity extends AppCompatActivity {

    private SessionToken st;
    private long albumId;
    private String albumName;
    private int photosQuantity;
    private boolean deviceHaveMenuButton = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SERVICE", "Get Photos from album - Connected");
            RequestService.RequestWorker requestWorker = (RequestService.RequestWorker) service;
            requestWorker.getPhotosFromAlbum(new ResponseCallback<PhotoAlbumModel>() {
                @Override
                public void onResponse(List<PhotoAlbumModel> items) {
                    setPhotos(items);
                }
            }, st.getUserId(), albumId);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SERVICE","Disconnected");
        }
    };


    private void setPhotos(List<PhotoAlbumModel> items) {
        View headerView = View.inflate(PhotoAlbumActivity.this,R.layout.photo_album_list_header,null);
        TextView albumTitle = (TextView) headerView.findViewById(R.id.photos_in_album_albumTitle);
        TextView albumQuantity = (TextView) headerView.findViewById(R.id.photos_in_album_photosQuantity);
        albumTitle.setText(" "+albumName);
        albumQuantity.setText(" "+items.size());

        List<List<PhotoAlbumModel>> mappedPhotosList = new ArrayList<List<PhotoAlbumModel>>();
        if(items.size() > 0){
            int rows = items.size()/4;
            int c = items.size()-1;
            for(int f = (items.size()/4);f>0;f--){
                List<PhotoAlbumModel> photoAlbumModel = new ArrayList<PhotoAlbumModel>();
                for(int i=0;i<4;i++){
                    photoAlbumModel.add(items.get(c));
                    c--;
                }
                mappedPhotosList.add(photoAlbumModel);

            }
            if(items.size() % 4 > 0){
                Log.d("!!!", " size " + (items.size() % 4));
                List<PhotoAlbumModel> additionalImagesRow = new ArrayList<PhotoAlbumModel>();

                for (int w = ((items.size() % 4)-1); w>=0; w--){
                    Log.d("!!!", " w " + w);
                    additionalImagesRow.add(items.get(w));
                }
                mappedPhotosList.add(additionalImagesRow);
            }
            //Log.d("!!!", " size " + mappedPhotosList.size());

        }

        ListView photosListView = (ListView) findViewById(R.id.photos_in_album_list);
        PhotoAlbumAdapter adapter = new PhotoAlbumAdapter(this,mappedPhotosList);
        photosListView.addHeaderView(headerView);
        DownloadImageService.loadImage((ImageView) headerView.findViewById(R.id.albumCoverImage),items.get(0).getPhoto_604());
        photosListView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_in_album);
        Intent intent = getIntent();
        photosQuantity = intent.getIntExtra("album_photos_quantity",0);
        albumId = intent.getLongExtra("album_id", 0);
        albumName = intent.getStringExtra("album_name");
        st = Session.getSession(this);


        ActionBar actionBar = getSupportActionBar();
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
        title.setText("Photos from Album");
        backButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGER", "PhotoAlbmsActivity is started!");
        if(Build.VERSION.SDK_INT <= 10){deviceHaveMenuButton = true;}
        bindService(new Intent(PhotoAlbumActivity.this,RequestService.class),serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
