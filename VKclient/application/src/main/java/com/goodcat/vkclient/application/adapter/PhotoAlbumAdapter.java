package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumModel;

import java.util.List;

public class PhotoAlbumAdapter extends ArrayAdapter<PhotoAlbumModel>{
    public PhotoAlbumAdapter(Context context, List<PhotoAlbumModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = View.inflate(getContext(), R.layout.photo_album_list_item,null);


        return currentView;
    }
}
