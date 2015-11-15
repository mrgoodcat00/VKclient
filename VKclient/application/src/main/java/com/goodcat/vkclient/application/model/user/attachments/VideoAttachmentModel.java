package com.goodcat.vkclient.application.model.user.attachments;

public class VideoAttachmentModel {

    public Integer id;
    public Integer owner_id;
    public String title;
    public String description;
    public Integer duration;
    public String photo_130;
    public String photo_320;
    public String photo_640;
    public Integer date;
    public Integer adding_date;
    public Integer views;
    public Integer comments;
    public String  player;
    public Integer access_key;
    public Integer processing;
    public Integer live;

    public Integer getId() {
        return id;
    }

    public Integer getOwner_id() {
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

    public Integer getDate() {
        return date;
    }

    public Integer getAdding_date() {
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

    public Integer getAccess_key() {
        return access_key;
    }

    public Integer getProcessing() {
        return processing;
    }

    public Integer getLive() {
        return live;
    }
}
