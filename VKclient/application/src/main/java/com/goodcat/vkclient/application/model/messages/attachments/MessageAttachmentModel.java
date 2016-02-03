package com.goodcat.vkclient.application.model.messages.attachments;


import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.model.user.attachments.PhotoAttachmentModel;
import com.goodcat.vkclient.application.model.user.attachments.VideoAttachmentModel;
import com.google.gson.annotations.SerializedName;

public class MessageAttachmentModel {

    @SerializedName("type")
    private String type;
    @SerializedName("photo")
    private PhotoAttachmentModel photo;
    @SerializedName("audio")
    private MusicModel audio;
    @SerializedName("video")
    private VideoAttachmentModel video;
    @SerializedName("wall")
    private WallTypeAttachmentModel wall;

    public String getType() {
        return type;
    }
    public PhotoAttachmentModel getPhoto() {
        return photo;
    }
    public MusicModel getAudio() {
        return audio;
    }
    public VideoAttachmentModel getVideo() {
        return video;
    }
    public WallTypeAttachmentModel getWall() {
        return wall;
    }

    /*@SerializedName("doc")
    private WallTypeAttachmentModel doc;
    @SerializedName("wall_reply")
    private WallTypeAttachmentModel wall_reply;
    @SerializedName("sticker")
    private WallTypeAttachmentModel sticker;*/

}
