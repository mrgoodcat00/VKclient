package com.goodcat.vkclient.application.model.user.attachments;

import com.google.gson.annotations.SerializedName;

public class VideoAttachmentModel {
    @SerializedName("id")
    public long id;

    @SerializedName("owner_id")
    public long ownerId;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("duration")
    public int duration;

    @SerializedName("photo_130")
    public String photo130;

    @SerializedName("photo_320")
    public String photo320;

    @SerializedName("photo_640")
    public String photo640;

    @SerializedName("date")
    public long date;

    @SerializedName("adding_date")
    public long addingDate;

    @SerializedName("views")
    public int views;

    @SerializedName("comments")
    public int comments;

    @SerializedName("player")
    public String  player;

    @SerializedName("access_key")
    public String accessKey;

    @SerializedName("processing")
    public int processing;

    @SerializedName("live")
    public int live;

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public String getPhoto130() {
        return photo130;
    }

    public String getPhoto320() {
        return photo320;
    }

    public String getPhoto640() {
        return photo640;
    }

    public long getDate() {
        return date;
    }

    public long getAddingDate() {
        return addingDate;
    }

    public int getViews() {
        return views;
    }

    public int getComments() {
        return comments;
    }

    public String getPlayer() {
        return player;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public int getProcessing() {
        return processing;
    }

    public int getLive() {
        return live;
    }
}
