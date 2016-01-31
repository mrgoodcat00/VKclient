package com.goodcat.vkclient.application.model.messages.attachments;

import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsCommentsCountModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsRepostsCountModel;

import java.util.List;

public class WallTypeAttachmentModel {
    private long id;
    private long to_id;
    private long from_id;
    private long date;
    private String text;
    private UserWallPostsCommentsCountModel comments;
    private UserWallPostsRepostsCountModel reposts;
    private List<UserWallAttachmentsModel> attachments;
    private long signer_id;
    private long copy_owner_id;
    private long copy_post_id;
    private long copy_text;
    public long getId() {
        return id;
    }
    public long getTo_id() {
        return to_id;
    }
    public long getFrom_id() {
        return from_id;
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
    public long getSigner_id() {
        return signer_id;
    }
    public long getCopy_owner_id() {
        return copy_owner_id;
    }
    public long getCopy_post_id() {
        return copy_post_id;
    }
    public long getCopy_text() {
        return copy_text;
    }
}
