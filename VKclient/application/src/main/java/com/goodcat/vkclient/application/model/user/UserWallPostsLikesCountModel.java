package com.goodcat.vkclient.application.model.user;

import com.google.gson.annotations.SerializedName;

public class UserWallPostsLikesCountModel {

    @SerializedName("count")
    private int count;

    @SerializedName("user_likes")
    private int userLikes;

    @SerializedName("can_like")
    private int canLike;

    @SerializedName("can_publish")
    private int canPublish;

    public int getCount() {
        return count;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public int getCanLike() {
        return canLike;
    }

    public int getCanPublish() {
        return canPublish;
    }
}
