package com.goodcat.vkclient.application.model.user;


import com.google.gson.annotations.SerializedName;

public class UserCountersModel {
    @SerializedName("albums")
    private int albums;

    @SerializedName("videos")
    private int videos;

    @SerializedName("audios")
    private int audios;

    @SerializedName("photos")
    private int photos;

    @SerializedName("notes")
    private int notes;

    @SerializedName("friends")
    private int friends;

    @SerializedName("groups")
    private int groups;

    @SerializedName("online_friends")
    private int onlineFriends;

    @SerializedName("mutual_friends")
    private int mutualFriends;

    @SerializedName("user_videos")
    private int userVideos;

    @SerializedName("followers")
    private int followers;

    public int getAlbums() {
        return albums;
    }

    public int getVideos() {
        return videos;
    }

    public int getAudios() {
        return audios;
    }

    public int getPhotos() {
        return photos;
    }

    public int getNotes() {
        return notes;
    }

    public int getFriends() {
        return friends;
    }

    public int getGroups() {
        return groups;
    }

    public int getOnlineFriends() {
        return onlineFriends;
    }

    public int getMutualFriends() {
        return mutualFriends;
    }

    public int getUserVideos() {
        return userVideos;
    }

    public int getFollowers() {
        return followers;
    }
}
