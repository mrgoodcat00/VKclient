package com.goodcat.vkclient.application.model.user;

import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWallPostsModel {
    @SerializedName("id")
    private Long id;

    @SerializedName("owner_id")
    private Long owner_id;

    @SerializedName("from_id")
    private Long from_id;

    @SerializedName("date")
    private Long date;

    @SerializedName("text")
    private String text;

    @SerializedName("reply_owner_id")
    private Long reply_owner_id;

    @SerializedName("reply_post_id")
    private Long reply_post_id;

    @SerializedName("friends_only")
    private Integer friends_only;

    @SerializedName("post_type")
    private String post_type;

    @SerializedName("attachments")
    private List<UserWallAttachmentsModel> attachments;

    @SerializedName("signer_id")
    private  Long signer_id;

    @SerializedName("copy_history")
    private List<UserResponseWallCopyHistoryModel> copy_history;

    @SerializedName("can_pin")
    private  Integer can_pin;

    @SerializedName("is_pinned")
    private Integer is_pinned;

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

    public String getText() {
        return text;
    }

    public Long getReply_owner_id() {
        return reply_owner_id;
    }

    public Long getReply_post_id() {
        return reply_post_id;
    }

    public Integer getFriends_only() {
        return friends_only;
    }

    public String getPost_type() {
        return post_type;
    }

    public List<UserWallAttachmentsModel> getAttachments() {
        return attachments;
    }

    public Long getSigner_id() {
        return signer_id;
    }

    public List<UserResponseWallCopyHistoryModel> getCopy_history() {
        return copy_history;
    }

    public Integer getCan_pin() {
        return can_pin;
    }

    public Integer getIs_pinned() {
        return is_pinned;
    }


   /* @SerializedName("post_source")
    private  String post_source;

    @SerializedName("comments")
    private   String comments;

    @SerializedName("likes")
    private  String likes;

    @SerializedName("reposts")
    private  String reposts;

    @SerializedName("geo")
    private  String geo;*/

}
