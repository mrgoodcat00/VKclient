package com.goodcat.vkclient.application.model.user.wall_post;


import com.google.gson.annotations.SerializedName;

public class UserWallProfilesModel {

    @SerializedName("id")
    private long id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("sex")
    private int sex;

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("text")
    private String text;

    @SerializedName("photo_50")
    private String photo50;

    @SerializedName("photo_100")
    private String photo100;

    @SerializedName("online")
    private int online;

    @SerializedName("online_app")
    private String onlineApp;

    @SerializedName("online_mobile")
    private int onlineMobile;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSex() {
        return sex;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getText() {
        return text;
    }

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public int getOnline() {
        return online;
    }

    public String getOnlineApp() {
        return onlineApp;
    }

    public int getOnlineMobile() {
        return onlineMobile;
    }
}
