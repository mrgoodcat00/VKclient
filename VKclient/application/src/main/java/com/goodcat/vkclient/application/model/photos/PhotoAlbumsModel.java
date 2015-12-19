package com.goodcat.vkclient.application.model.photos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoAlbumsModel {

    @SerializedName("id")
    private long id;

    @SerializedName("thumb_id")
    private long thumb_id;

    @SerializedName("owner_id")
    private long owner_id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("created")
    private long created;

    @SerializedName("updated")
    private long updated;

    @SerializedName("size")
    private int size;

    @SerializedName("can_upload")
    private int can_upload;

    @SerializedName("comments_disabled")
    private int comments_disabled;

    @SerializedName("thumb_src")
    private String thumb_src;

    @SerializedName("sizes")
    private List<PhotoSizesModel> sizes;

    public List<PhotoSizesModel> getSizes() {
        return sizes;
    }

    public long getId() {
        return id;
    }

    public long getThumb_id() {
        return thumb_id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public int getSize() {
        return size;
    }

    public int getCan_upload() {
        return can_upload;
    }

    public int getComments_disabled() {
        return comments_disabled;
    }

    public String getThumb_src() {
        return thumb_src;
    }
}
