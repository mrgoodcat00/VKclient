package com.goodcat.vkclient.application.model.photos;

import com.google.gson.annotations.SerializedName;

public class PhotoAlbumModel {

    @SerializedName("id")
    private long id;

    @SerializedName("album_id")
    private int album_id;

    @SerializedName("owner_id")
    private long owner_id;

    @SerializedName("photo_75")
    private String photo_75;

    @SerializedName("photo_130")
    private String photo_130;

    @SerializedName("photo_604")
    private String photo_604;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private long date;

    @SerializedName("post_id")
    private long post_id;

    public long getId() {
        return id;
    }

    private String albumName;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public String getPhoto_75() {
        return photo_75;
    }

    public String getPhoto_130() {
        return photo_130;
    }

    public String getPhoto_604() {
        return photo_604;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public long getDate() {
        return date;
    }

    public long getPost_id() {
        return post_id;
    }

    /*  likes: {
                    user_likes: 0,
                        count: 4
                     },
                        comments: {
                            count: 0
                        },
                    can_comment: 1,
                            tags: {
                        count: 0
                    }*/





}
