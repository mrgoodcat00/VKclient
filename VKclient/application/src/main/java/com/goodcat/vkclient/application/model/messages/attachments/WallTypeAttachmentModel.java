package com.goodcat.vkclient.application.model.messages.attachments;

import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsCommentsCountModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsRepostsCountModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WallTypeAttachmentModel {
    @SerializedName("id")
    private long id;
    @SerializedName("to_id")
    private long toId;
    @SerializedName("from_id")
    private long fromId;
    @SerializedName("date")
    private long date;
    @SerializedName("text")
    private String text;
    @SerializedName("comments")
    private UserWallPostsCommentsCountModel comments;
    @SerializedName("reposts")
    private UserWallPostsRepostsCountModel reposts;
    @SerializedName("attachments")
    private List<UserWallAttachmentsModel> attachments;
    @SerializedName("copy_history")
    private List<UserWallPostsModel> copyHistory;



    public long getId() {
        return id;
    }
    public long getToId() {
        return toId;
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
    public UserWallPostsCommentsCountModel getComments() {
        return comments;
    }
    public UserWallPostsRepostsCountModel getReposts() {
        return reposts;
    }
    public List<UserWallAttachmentsModel> getAttachments() {
        return attachments;
    }
    public List<UserWallPostsModel> getCopyHistory() {
        return copyHistory;
    }

     /*
    seems that params are not able...
    @SerializedName("copy_owner_id")
    private long copyOwnerId;
    @SerializedName("copy_post_id")
    private long copyPostId;
    @SerializedName("copy_text")
    private long copyText;
    @SerializedName("signer_id")
    private long signerId;*/
/*    public long getCopyOwnerId() {
        return copyOwnerId;
    }

    public long getCopyPostId() {
        return copyPostId;
    }

    public long getCopyText() {
        return copyText;
    }

     public long getSignerId() {
        return signerId;
    }*/
}
