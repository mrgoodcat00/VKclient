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

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public void setAudios(int audios) {
        this.audios = audios;
    }

    public void setPhotos(int photos) {
        this.photos = photos;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public void setOnlineFriends(int onlineFriends) {
        this.onlineFriends = onlineFriends;
    }

    public void setMutualFriends(int mutualFriends) {
        this.mutualFriends = mutualFriends;
    }

    public void setUserVideos(int userVideos) {
        this.userVideos = userVideos;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
