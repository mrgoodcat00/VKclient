package com.goodcat.vkclient.application.model.music;

import com.google.gson.annotations.SerializedName;

public class MusicModel {

    @SerializedName("id")
    private long id;

    @SerializedName("owner_id")
    private long ownerId;

    @SerializedName("artist")
    private String artist;

    @SerializedName("title")
    private String title;

    @SerializedName("duration")
    private int duration;

    @SerializedName("url")
    private String url;

    @SerializedName("lyrics_id")
    private long lyricsId;

    @SerializedName("album_id")
    private long albumId;

    @SerializedName("genre_id")
    private long genreId;

    @SerializedName("date")
    private long date;

    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public long getLyricsId() {
        return lyricsId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getGenreId() {
        return genreId;
    }

    public long getDate() {
        return date;
    }




}
