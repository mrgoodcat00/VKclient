package com.goodcat.vkclient.application.model.user.attachments;


import com.google.gson.annotations.SerializedName;

public class UserWallAttachmentsModel {

    @SerializedName("type")
    private String type;

    @SerializedName("video")
    private VideoAttachmentModel video;

    @SerializedName("photo")
    private PhotoAttachmentModel photo;

    @SerializedName("wall")
    private WallMessageAttachmentModel wall;

    /*@SerializedName("posted_photo")
    private PhotoAttachmentModel posted_photo;

    @SerializedName("audio")
    private PhotoAttachmentModel audio;*/





    /*@SerializedName("wall_reply")
    private PhotoAttachmentModel wall_reply;

    @SerializedName("sticker")
    private PhotoAttachmentModel sticker;*/


    public String getType() {
        return type;
    }

    public String getStyledType() {
        return type;
    }

    public VideoAttachmentModel getVideo() {
        return video;
    }

    public PhotoAttachmentModel getPhoto() {
        return photo;
    }

    public WallMessageAttachmentModel getWall() {
        return wall;
    }

}
