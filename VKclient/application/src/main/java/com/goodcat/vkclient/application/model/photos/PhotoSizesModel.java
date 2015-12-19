package com.goodcat.vkclient.application.model.photos;


import com.google.gson.annotations.SerializedName;

public class PhotoSizesModel {
    @SerializedName("src")
    private String src;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("type")
    private char type;

    public String getSrc() {
        return src;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char getType() {
        return type;
    }
}
