package com.goodcat.vkclient.application.model.user.attachments;


import com.google.gson.annotations.SerializedName;

public class UserWallAttachmentsModel {

    @SerializedName("type")
    private String type;

    @SerializedName("video")
    private VideoAttachmentModel video;

    @SerializedName("photo")
    private PhotoAttachmentModel photo;

    public String getType() {
        return type;
    }

    public VideoAttachmentModel getVideo() {
        return video;
    }

    public PhotoAttachmentModel getPhoto() {
        return photo;
    }
}
