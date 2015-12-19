package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumsModel;
import com.goodcat.vkclient.application.model.photos.PhotoSizesModel;
import com.goodcat.vkclient.application.service.DownloadImageService;

import java.util.List;

public class PhotoAlbumsAdapter extends ArrayAdapter<PhotoAlbumsModel>{

    public PhotoAlbumsAdapter(Context context, List<PhotoAlbumsModel> objects) {
        super(context, 0, objects);
        DownloadImageService.init(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView;
        ViewAlbumsHolder holder;
        if (convertView == null) {
            holder = new ViewAlbumsHolder();
            currentView = View.inflate(getContext(),R.layout.photo_albums_list_item, null);
            holder.albumTitle = (TextView) currentView.findViewById(R.id.album_title);
            holder.albumPhotosQuantity = (TextView) currentView.findViewById(R.id.album_photos_quantity);
            holder.albumPreView = (ImageView) currentView.findViewById(R.id.albumPreView);
            currentView.setTag(holder);
        } else {
            currentView = convertView;
            holder = (ViewAlbumsHolder) currentView.getTag();
        }
        PhotoAlbumsModel photoAlbumModel = getItem(position);
        List<PhotoSizesModel> lpsm = photoAlbumModel.getSizes();
        for(PhotoSizesModel psm:lpsm){
            if(psm.getType() == 'x' || psm.getType() == 'p' || psm.getType() == 'q' || psm.getType() == 'r'){
                DownloadImageService.loadImage(holder.albumPreView, psm.getSrc());
                break;
            }
        }
        holder.albumPhotosQuantity.setText(""+photoAlbumModel.getSize()+" photo");
        holder.albumTitle.setText(photoAlbumModel.getTitle());
        return currentView;
    }

    public static class ViewAlbumsHolder {
        TextView albumTitle;
        TextView albumPhotosQuantity;
        ImageView albumPreView;
    }
}
