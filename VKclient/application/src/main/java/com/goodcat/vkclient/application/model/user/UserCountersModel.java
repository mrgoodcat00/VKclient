package com.goodcat.vkclient.application.model.user;


import com.google.gson.annotations.SerializedName;

public class UserCountersModel {



    @SerializedName("albums")
    private Integer albums;

    @SerializedName("videos")
    private Integer videos;

    @SerializedName("audios")
    private Integer audios;

    @SerializedName("photos")
    private Integer photos;

    @SerializedName("notes")
    private Integer notes;

    @SerializedName("friends")
    private Integer friends;

    @SerializedName("groups")
    private Integer groups;

    @SerializedName("online_friends")
    private Integer online_friends;

    @SerializedName("mutual_friends")
    private Integer mutual_friends;

    @SerializedName("user_videos")
    private Integer user_videos;

    @SerializedName("followers")
    private Integer followers;

    public Integer getAlbums() {
        return albums;
    }

    public void setAlbums(Integer albums) {
        this.albums = albums;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getAudios() {
        return audios;
    }

    public void setAudios(Integer audios) {
        this.audios = audios;
    }

    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }

    public Integer getNotes() {
        return notes;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }

    public Integer getFriends() {
        return friends;
    }

    public void setFriends(Integer friends) {
        this.friends = friends;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getOnline_friends() {
        return online_friends;
    }

    public void setOnline_friends(Integer online_friends) {
        this.online_friends = online_friends;
    }

    public Integer getMutual_friends() {
        return mutual_friends;
    }

    public void setMutual_friends(Integer mutual_friends) {
        this.mutual_friends = mutual_friends;
    }

    public Integer getUser_videos() {
        return user_videos;
    }

    public void setUser_videos(Integer user_videos) {
        this.user_videos = user_videos;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }
}
