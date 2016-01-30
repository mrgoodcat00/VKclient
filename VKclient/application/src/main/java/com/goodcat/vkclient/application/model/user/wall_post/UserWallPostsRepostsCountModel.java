package com.goodcat.vkclient.application.model.user.wall_post;

import com.google.gson.annotations.SerializedName;

public class UserWallPostsRepostsCountModel {

    @SerializedName("count")
    private int count;

    @SerializedName("user_reposted")
    private int userReposted;

    public int getCount() {
        return count;
    }

    public int getUserReposted() {
        return userReposted;
    }
}
