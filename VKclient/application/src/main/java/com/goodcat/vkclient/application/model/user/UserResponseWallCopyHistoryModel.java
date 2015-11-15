package com.goodcat.vkclient.application.model.user;


import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponseWallCopyHistoryModel{

    @SerializedName("id")
    public Integer id;

    @SerializedName("owner_id")
    public Integer owner_id;

    @SerializedName("from_id")
    public Integer from_id;

    @SerializedName("date")
    public Integer date;

    @SerializedName("post_type")
    public String post_type;

    @SerializedName("text")
    public String text;

    @SerializedName("attachments")
    private static List<UserWallAttachmentsModel> attachments;

    public static List<UserWallAttachmentsModel> getAttachments() {
        return attachments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public Integer getFrom_id() {
        return from_id;
    }

    public void setFrom_id(Integer from_id) {
        this.from_id = from_id;
    }

    public Integer getDate() {
        return date / (24 * 60 * 60 * 1000);
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
