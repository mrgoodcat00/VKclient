package com.goodcat.vkclient.application.model.user.attachments;

import com.google.gson.annotations.SerializedName;

public class PhotoAttachmentModel {

    @SerializedName("id")
    private long id;

    @SerializedName("album_id")
    private long albumId;

    @SerializedName("owner_id")
    private long ownerId;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private long date;

    @SerializedName("photo_75")
    private String photo75;

    @SerializedName("photo_130")
    private String photo130;

    @SerializedName("photo_604")
    private String photo604;

    @SerializedName("photo_807")
    private String photo807;

    @SerializedName("photo_1280")
    private String photo1280;

    @SerializedName("photo_2560")
    private String photo2560;

    @SerializedName("access_key")
    private String accessKey;

    public long getId() {
        return id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public long getDate() {
        return date;
    }

    public String getPhoto75() {
        return photo75;
    }

    public String getPhoto130() {
        return photo130;
    }

    public String getPhoto604() {
        return photo604;
    }

    public String getPhoto807() {
        return photo807;
    }

    public String getPhoto1280() {
        return photo1280;
    }

    public String getPhoto2560() {
        return photo2560;
    }

    public String getAccessKey() {
        return accessKey;
    }
}
