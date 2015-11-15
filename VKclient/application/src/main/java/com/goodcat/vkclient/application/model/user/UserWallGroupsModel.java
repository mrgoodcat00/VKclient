package com.goodcat.vkclient.application.model.user;

import com.google.gson.annotations.SerializedName;

public class UserWallGroupsModel {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private Long date;

    @SerializedName("can_edit")
    private Integer can_edit;

    @SerializedName("can_delete")
    private Integer can_delete;

    @SerializedName("can_pin")
    private Integer can_pin;

    @SerializedName("post_type")
    private String post_type;

    @SerializedName("text")
    private String text;

    @SerializedName("photo_50")
    private String photo_50;

    public String getPhoto_50() {
        return photo_50;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public Long getDate() {
        return date;
    }

    public Integer getCan_edit() {
        return can_edit;
    }

    public Integer getCan_delete() {
        return can_delete;
    }

    public Integer getCan_pin() {
        return can_pin;
    }

    public String getPost_type() {
        return post_type;
    }

}
