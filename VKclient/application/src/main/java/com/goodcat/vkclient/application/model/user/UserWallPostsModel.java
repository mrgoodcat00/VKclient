package com.goodcat.vkclient.application.model.user;

import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWallPostsModel {
    @SerializedName("id")
    private long id;

    @SerializedName("owner_id")
    private long ownerId;

    @SerializedName("from_id")
    private long fromId;

    @SerializedName("date")
    private long date;

    @SerializedName("text")
    private String text;

    @SerializedName("reply_owner_id")
    private long replyOwnerId;

    @SerializedName("reply_post_id")
    private long replyPostId;

    @SerializedName("friends_only")
    private int friendsOnly;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("attachments")
    private List<UserWallAttachmentsModel> attachments;

    @SerializedName("comments")
    private UserWallPostsCommentsCountModel comments;

    @SerializedName("likes")
    private UserWallPostsLikesCountModel likes;

    @SerializedName("reposts")
    private UserWallPostsRepostsCountModel reposts;

    @SerializedName("signer_id")
    private  long signerId;

    @SerializedName("copy_history")
    private List<UserResponseWallCopyHistoryModel> copyHistory;

    @SerializedName("can_pin")
    private  int canPin;

    @SerializedName("is_pinned")
    private int isPinned;

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getFromId() {
        return fromId;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public long getReplyOwnerId() {
        return replyOwnerId;
    }

    public long getReplyPostId() {
        return replyPostId;
    }

    public int getFriendsOnly() {
        return friendsOnly;
    }

    public String getPostType() {
        return postType;
    }

    public List<UserWallAttachmentsModel> getAttachments() {
        return attachments;
    }

    public UserWallPostsCommentsCountModel getComments() {
        return comments;
    }

    public UserWallPostsLikesCountModel getLikes() {
        return likes;
    }

    public UserWallPostsRepostsCountModel getReposts() {
        return reposts;
    }

    public long getSignerId() {
        return signerId;
    }

    public List<UserResponseWallCopyHistoryModel> getCopyHistory() {
        return copyHistory;
    }

    public int getCanPin() {
        return canPin;
    }

    public int getIsPinned() {
        return isPinned;
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
