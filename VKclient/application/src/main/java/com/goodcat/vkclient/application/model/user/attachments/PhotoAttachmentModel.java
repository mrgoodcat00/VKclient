package com.goodcat.vkclient.application.model.user.attachments;

import com.google.gson.annotations.SerializedName;

public class PhotoAttachmentModel {

    @SerializedName("id")
    private Long id;

    @SerializedName("album_id")
    private Long album_id;

    @SerializedName("owner_id")
    private Long owner_id;

    @SerializedName("user_id")
    private Long user_id;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private Long date;

    @SerializedName("photo_75")
    private String photo_75;

    @SerializedName("photo_130")
    private String photo_130;

    @SerializedName("photo_604")
    private String photo_604;

    @SerializedName("photo_807")
    private String photo_807;

    @SerializedName("photo_1280")
    private String photo_1280;

    @SerializedName("photo_2560")
    private String photo_2560;

    @SerializedName("access_key")
    private String access_key;

    public String getAccess_key() {return access_key;}

    public Long getId() {
        return id;
    }

    public Long getAlbum_id() {
        return album_id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getText() {
        return text;
    }

    public Long getDate() {
        return date;
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

    public String getPhoto_807() {
        return photo_807;
    }

    public String getPhoto_1280() {
        return photo_1280;
    }

    public String getPhoto_2560() {
        return photo_2560;
    }
}
