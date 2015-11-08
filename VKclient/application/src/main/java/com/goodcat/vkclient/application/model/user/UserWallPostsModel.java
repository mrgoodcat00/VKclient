package com.goodcat.vkclient.application.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserWallPostsModel {
    @SerializedName("id")
    private Long id;

    @SerializedName("owner_id")
    private Long owner_id;

    @SerializedName("from_id")
    private Long from_id;

    @SerializedName("date")
    private Long date;

    @SerializedName("text")
    private String text;

    @SerializedName("reply_owner_id")
    private Long reply_owner_id;

    @SerializedName("reply_post_id")
    private Long reply_post_id;

    @SerializedName("friends_only")
    private Integer friends_only;

    //@SerializedName("comments")
    //private static String comments;

   // @SerializedName("likes")
   // private static String likes;

    //@SerializedName("reposts")
    //private static String reposts;

    @SerializedName("post_type")
    private String post_type;

   /* @SerializedName("post_source")
    private static String post_source;

    @SerializedName("attachments")
    private static String attachments;

    @SerializedName("geo")
    private static String geo;*/

    @SerializedName("signer_id")
    private  Long signer_id;



    @SerializedName("copy_history")
    private List<UserResponseWallCopyHistoryModel> copy_history;

    public List<UserResponseWallCopyHistoryModel> getCopy_history() {
        return copy_history;
    }

    public void setCopy_history(List<UserResponseWallCopyHistoryModel> copy_history) {
        this.copy_history = copy_history;
    }

    @SerializedName("can_pin")
    private  Integer can_pin;

    @SerializedName("is_pinned")
    private Integer is_pinned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public Long getFrom_id() {
        return from_id;
    }

    public void setFrom_id(Long from_id) {
        this.from_id = from_id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getReply_owner_id() {
        return reply_owner_id;
    }

    public void setReply_owner_id(Long reply_owner_id) {
        this.reply_owner_id = reply_owner_id;
    }

    public Long getReply_post_id() {
        return reply_post_id;
    }

    public void setReply_post_id(Long reply_post_id) {
        this.reply_post_id = reply_post_id;
    }

    public Integer getFriends_only() {
        return friends_only;
    }

    public void setFriends_only(Integer friends_only) {
        this.friends_only = friends_only;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }


    public Long getSigner_id() {
        return signer_id;
    }

    public void setSigner_id(Long signer_id) {
        this.signer_id = signer_id;
    }


    public Integer getCan_pin() {
        return can_pin;
    }

    public void setCan_pin(Integer can_pin) {
        this.can_pin = can_pin;
    }

    public Integer getIs_pinned() {
        return is_pinned;
    }

    public void setIs_pinned(Integer is_pinned) {
        this.is_pinned = is_pinned;
    }
}
