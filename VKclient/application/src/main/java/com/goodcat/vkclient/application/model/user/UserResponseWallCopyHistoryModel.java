package com.goodcat.vkclient.application.model.user;


import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponseWallCopyHistoryModel{

    @SerializedName("id")
    public long id;

    @SerializedName("owner_id")
    public long ownerId;

    @SerializedName("from_id")
    public long fromId;

    @SerializedName("date")
    public long date;

    @SerializedName("post_type")
    public String postType;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getFromId() {
        return fromId;
    }

    public Long getDate() {
        return date;
    }

    public String getPostType() {
        return postType;
    }

    public String getText() {
        return text;
    }
}
