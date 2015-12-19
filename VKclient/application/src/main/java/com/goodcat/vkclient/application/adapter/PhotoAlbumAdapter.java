package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumModel;
import com.goodcat.vkclient.application.service.DownloadImageService;

import java.util.List;

public class PhotoAlbumAdapter extends ArrayAdapter<List<PhotoAlbumModel>>{
    private int photosQuantity;
    public PhotoAlbumAdapter(Context context, List<List<PhotoAlbumModel>> objects) {
        super(context, 0, objects);
        this.photosQuantity = objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView;
        ViewAlbumsHolder holder;
        if (convertView == null) {
            holder = new ViewAlbumsHolder();
            currentView = View.inflate(getContext(),R.layout.photo_album_list_item, null);
            holder.photoFromAlbum1 = (ImageView) currentView.findViewById(R.id.imageView4);
            holder.photoFromAlbum2 = (ImageView) currentView.findViewById(R.id.imageView5);
            holder.photoFromAlbum3 = (ImageView) currentView.findViewById(R.id.imageView6);
            holder.photoFromAlbum4 = (ImageView) currentView.findViewById(R.id.imageView7);
            currentView.setTag(holder);
        } else {
            currentView = convertView;
            holder = (ViewAlbumsHolder) currentView.getTag();
        }
        int count=0;
        List<PhotoAlbumModel> photoAlbumModel = getItem(position);
        if(photoAlbumModel.size()<4){
            for (PhotoAlbumModel pam : photoAlbumModel) {
                Log.d("PhotoADAPTER", " counter " + count);
                setImageFromAlbum(holder, count, pam.getPhoto_130());
                count++;
            }
            for(int q = photoAlbumModel.size(); q < 4; q++ ){
                Log.d("PhotoADAPTER","counter "+q);
                hideUnusedViews(q,holder);
            }
        } else {
            for (PhotoAlbumModel pam : photoAlbumModel) {
                Log.d("PhotoADAPTER", " counter " + count);
                setImageFromAlbum(holder, count, pam.getPhoto_130());
                count++;
            }
        }
        return currentView;
    }

    private void setImageFromAlbum(ViewAlbumsHolder holder, int iterator, String url){
        switch (iterator){
            case 0:
                DownloadImageService.loadImage(holder.photoFromAlbum1, url);
                break;
            case 1:
                DownloadImageService.loadImage(holder.photoFromAlbum2, url);
                break;
            case 2:
                DownloadImageService.loadImage(holder.photoFromAlbum3, url);
                break;
            case 3:
                DownloadImageService.loadImage(holder.photoFromAlbum4, url);
                break;
            default:
                break;
        }
    }

    private void hideUnusedViews(int iterator,ViewAlbumsHolder holder){
        switch (iterator){
            case 0:
                holder.photoFromAlbum1.setVisibility(View.GONE);
                break;
            case 1:
                holder.photoFromAlbum2.setVisibility(View.GONE);
                break;
            case 2:
                holder.photoFromAlbum3.setVisibility(View.GONE);
                break;
            case 3:
                holder.photoFromAlbum4.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public static class ViewAlbumsHolder {
        ImageView photoFromAlbum1;
        ImageView photoFromAlbum2;
        ImageView photoFromAlbum3;
        ImageView photoFromAlbum4;
    }
}
