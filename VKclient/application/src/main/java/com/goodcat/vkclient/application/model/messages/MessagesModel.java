package com.goodcat.vkclient.application.model.messages;

public class MessagesModel {

    private String userLogoUrl;
    private String senderLogoUrl;
    private String senderName;
    private String senderTextExcerpt;
    private String postTime;

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }

    public String getSenderLogoUrl() {
        return senderLogoUrl;
    }

    public void setSenderLogoUrl(String senderLogoUrl) {
        this.senderLogoUrl = senderLogoUrl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderTextExcerpt() {
        return senderTextExcerpt;
    }

    public void setSenderTextExcerpt(String senderTextExcerpt) {
        this.senderTextExcerpt = senderTextExcerpt;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
