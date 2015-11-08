package com.goodcat.vkclient.application.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.PhotoAlbumAdapter;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_in_album);



        View headerView = View.inflate(PhotoAlbumActivity.this,R.layout.photo_album_list_header,null);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if(bundle!=null)
        {
            String albName =(String) bundle.get("album_name");
            String photosQuantity =(String) bundle.get("album_photos_quantity");

            TextView albumTitle = (TextView) headerView.findViewById(R.id.photos_in_album_albumTitle);
            TextView albumQuantity = (TextView) headerView.findViewById(R.id.photos_in_album_photosQuantity);

            albumTitle.setText(" "+albName);
            albumQuantity.setText(" "+photosQuantity);
        }

        ListView photosListView = (ListView) findViewById(R.id.photos_in_album_list);


        List<PhotoAlbumModel> albumModelList = new ArrayList<PhotoAlbumModel>();

        for(int i=0; i<24; i++) {
            PhotoAlbumModel singleAlbum = new PhotoAlbumModel();
            albumModelList.add(singleAlbum);
        }

        PhotoAlbumAdapter adapter = new PhotoAlbumAdapter(this,albumModelList);

        photosListView.addHeaderView(headerView);

        photosListView.setAdapter(adapter);





        Button back = (Button) findViewById(R.id.photos_in_album_header_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
