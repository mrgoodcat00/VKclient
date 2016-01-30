package com.goodcat.vkclient.application.model.user.wall_post;

import com.google.gson.annotations.SerializedName;

public class UserWallGroupsModel {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private long date;

    @SerializedName("can_edit")
    private int canEdit;

    @SerializedName("can_delete")
    private int canDelete;

    @SerializedName("can_pin")
    private int canPin;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("text")
    private String text;

    @SerializedName("photo_50")
    private String photo50;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }

    public int getCanEdit() {
        return canEdit;
    }

    public int getCanDelete() {
        return canDelete;
    }

    public int getCanPin() {
        return canPin;
    }

    public String getPostType() {
        return postType;
    }

    public String getText() {
        return text;
    }

    public String getPhoto50() {
        return photo50;
    }
}
