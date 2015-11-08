package com.goodcat.vkclient.application.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.PhotoAlbumsAdapter;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumsModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);


        ListView albumsList = (ListView) findViewById(R.id.album_photo_list_albums);

        List<PhotoAlbumsModel> albumModel = new ArrayList<PhotoAlbumsModel>();

        String[] albumsNames = {"All photos", "All photos with me", "John Doe Birth Day", "Just another day",
                                "My cat", "Holidays in NY", "Holidays in LA", "Saved Photos"};

        for(int i=0;i<7;i++){
            PhotoAlbumsModel model = new PhotoAlbumsModel();
            model.setAlbumTitle(albumsNames[i]);
            model.setPhotosQuantity(""+i*23);
            albumModel.add(model);
        }

        PhotoAlbumsAdapter adapter = new PhotoAlbumsAdapter(this,albumModel);

        albumsList.setAdapter(adapter);

        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoAlbumsModel clickedAlbum = (PhotoAlbumsModel) parent.getItemAtPosition(position);

                Intent newIntent = new Intent(PhotoAlbumsActivity.this,PhotoAlbumActivity.class);

                newIntent.putExtra("album_name",clickedAlbum.getAlbumTitle());
                newIntent.putExtra("album_photos_quantity",clickedAlbum.getPhotosQuantity());

                startActivity(newIntent);
            }
        });


        Button back = (Button) findViewById(R.id.main_header_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
