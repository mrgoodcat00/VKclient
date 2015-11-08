package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumsModel;

import java.util.List;

public class PhotoAlbumsAdapter extends ArrayAdapter<PhotoAlbumsModel>{


    public PhotoAlbumsAdapter(Context context, List<PhotoAlbumsModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;

        if(itemView == null){
            itemView = View.inflate(getContext(),R.layout.photo_albums_list_item, null);
        }

        TextView albumTitle = (TextView) itemView.findViewById(R.id.album_title);
        TextView albumPhotosQuantity = (TextView) itemView.findViewById(R.id.album_photos_quantity);

        PhotoAlbumsModel newAlbum = getItem(position);

        albumPhotosQuantity.setText(newAlbum.getPhotosQuantity());
        albumTitle.setText(newAlbum.getAlbumTitle());

        return itemView;
    }
}
