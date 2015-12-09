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


    public void setId(long id) {
        this.id = id;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLyricsId(long lyricsId) {
        this.lyricsId = lyricsId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
