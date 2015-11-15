package com.goodcat.vkclient.application.model.user;


import com.google.gson.annotations.SerializedName;

public class UserWallProfilesModel {

    @SerializedName("id")
    private Long id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("sex")
    private Integer sex;

    @SerializedName("screen_name")
    private String screen_name;

    @SerializedName("text")
    private String text;

    @SerializedName("photo_50")
    private String photo_50;

    @SerializedName("photo_100")
    private String photo_100;

    @SerializedName("online")
    private Integer online;

    @SerializedName("online_app")
    private String online_app;

    @SerializedName("online_mobile")
    private Integer online_mobile;

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getSex() {
        return sex;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getText() {
        return text;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public Integer getOnline() {
        return online;
    }

    public String getOnline_app() {
        return online_app;
    }

    public Integer getOnline_mobile() {
        return online_mobile;
    }
}
