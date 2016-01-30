package com.goodcat.vkclient.application.model.user.wall_post;

import com.google.gson.annotations.SerializedName;

public class UserWallPostsCommentsCountModel {

    @SerializedName("count")
    private int count;

    @SerializedName("can_post")
    private int canPost;

    public int getCount() {
        return count;
    }

    public int getCanPost() {
        return canPost;
    }
}
