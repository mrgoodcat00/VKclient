package com.goodcat.vkclient.application.model.messages;

import com.google.gson.annotations.SerializedName;

public class DialogModel {
    @SerializedName("in_read")
    private int in_read;

    @SerializedName("out_read")
    private int out_read;

    @SerializedName("message")
    private MessagesModel message;

    public int getIn_read() {
        return in_read;
    }

    public int getOut_read() {
        return out_read;
    }

    public MessagesModel getMessage() {
        return message;
    }
}
