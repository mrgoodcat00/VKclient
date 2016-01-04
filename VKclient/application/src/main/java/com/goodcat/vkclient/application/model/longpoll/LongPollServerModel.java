package com.goodcat.vkclient.application.model.longpoll;

import com.google.gson.annotations.SerializedName;

public class LongPollServerModel {

    @SerializedName("key")
    private String key;

    @SerializedName("server")
    private String server;

    @SerializedName("ts")
    private long ts;

    public String getKey() {
        return key;
    }

    public String getServer() {
        return server;
    }

    public long getTs() {
        return ts;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

}
