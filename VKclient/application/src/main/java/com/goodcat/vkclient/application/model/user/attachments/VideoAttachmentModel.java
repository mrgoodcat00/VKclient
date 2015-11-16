package com.goodcat.vkclient.application.model.user.attachments;

import com.google.gson.annotations.SerializedName;

public class VideoAttachmentModel {
    @SerializedName("id")
    public Long id;

    @SerializedName("owner_id")
    public Long owner_id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("duration")
    public Integer duration;

    @SerializedName("photo_130")
    public String photo_130;

    @SerializedName("photo_320")
    public String photo_320;

    @SerializedName("photo_640")
    public String photo_640;

    @SerializedName("date")
    public Long date;

    @SerializedName("adding_date")
    public Long adding_date;

    @SerializedName("views")
    public Integer views;

    @SerializedName("comments")
    public Integer comments;

    @SerializedName("player")
    public String  player;

    @SerializedName("access_key")
    public String access_key;

    @SerializedName("processing")
    public Integer processing;

    @SerializedName("live")
    public Integer live;

    public Long getId() {
        return id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getPhoto_130() {
        return photo_130;
    }

    public String getPhoto_320() {
        return photo_320;
    }

    public String getPhoto_640() {
        return photo_640;
    }

    public Long getDate() {
        return date;
    }

    public Long getAdding_date() {
        return adding_date;
    }

    public Integer getViews() {
        return views;
    }

    public Integer getComments() {
        return comments;
    }

    public String getPlayer() {
        return player;
    }

    public String getAccess_key() {
        return access_key;
    }

    public Integer getProcessing() {
        return processing;
    }

    public Integer getLive() {
        return live;
    }
}
