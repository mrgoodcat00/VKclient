package com.goodcat.vkclient.application.model.group;

import com.google.gson.annotations.SerializedName;

public class GroupModel {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("screenName")
    private String screenName;
    @SerializedName("isClosed")
    private int isClosed;
    @SerializedName("type")
    private String type;
    @SerializedName("isAdmin")
    private int isAdmin;
    @SerializedName("isMember")
    private int isMember;
    @SerializedName("description")
    private String description;
    @SerializedName("photo50")
    private String photo50;
    @SerializedName("photo100")
    private String photo100;
    @SerializedName("photo200")
    private String  photo200;
    @SerializedName("deactivated")
    private int deactivated;
    @SerializedName("has_photo")
    private int has_photo;
    @SerializedName("status")
    private int status;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public String getType() {
        return type;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public int getIsMember() {
        return isMember;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public String getPhoto200() {
        return photo200;
    }

    public int getDeactivated() {
        return deactivated;
    }

    public int getHas_photo() {
        return has_photo;
    }

    public int getStatus() {
        return status;
    }
}
