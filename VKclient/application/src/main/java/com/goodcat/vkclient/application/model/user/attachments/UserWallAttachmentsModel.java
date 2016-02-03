package com.goodcat.vkclient.application.model.user.attachments;


import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsModel;
import com.google.gson.annotations.SerializedName;

public class UserWallAttachmentsModel {

    @SerializedName("type")
    private String type;
    @SerializedName("video")
    private VideoAttachmentModel video;
    @SerializedName("photo")
    private PhotoAttachmentModel photo;
    @SerializedName("audio")
    private MusicModel audio;
    @SerializedName("wall")
    private UserWallPostsModel wall;

   /* @SerializedName("posted_photo")
    private WallTypeAttachmentModel posted_photo;
    @SerializedName("audio")
    private WallTypeAttachmentModel audio;
    @SerializedName("doc")
    private WallTypeAttachmentModel doc;
    @SerializedName("graffiti")
    private WallTypeAttachmentModel graffiti;
    @SerializedName("link")
    private WallTypeAttachmentModel link;
    @SerializedName("note")
    private WallTypeAttachmentModel note;
    @SerializedName("app")
    private WallTypeAttachmentModel app;
    @SerializedName("poll")
    private WallTypeAttachmentModel poll;
    @SerializedName("page")
    private WallTypeAttachmentModel page;
    @SerializedName("album")
    private WallTypeAttachmentModel album;
    @SerializedName("photos_list")
    private WallTypeAttachmentModel photos_list;*/


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

    public UserWallPostsModel getWall() {
        return wall;
    }

    public MusicModel getAudio() {
        return audio;
    }

}
