package com.goodcat.vkclient.application.model.user;


import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponseWallCopyHistoryModel{

    @SerializedName("id")
    public Long id;

    @SerializedName("owner_id")
    public Long owner_id;

    @SerializedName("from_id")
    public Long from_id;

    @SerializedName("date")
    public Long date;

    @SerializedName("post_type")
    public String post_type;

    @SerializedName("text")
    public String text;

    @SerializedName("attachments")
    private List<UserWallAttachmentsModel> attachments;

    public List<UserWallAttachmentsModel> getAttachments() {
        return attachments;
    }

    public Long getId() {
        return id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public Long getFrom_id() {
        return from_id;
    }

    public Long getDate() {
        return date;
    }

    public String getPost_type() {
        return post_type;
    }

    public String getText() {
        return text;
    }
}
