package com.goodcat.vkclient.application.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonParseCountVariableModel<T> {

    @SerializedName("count")
    private int count;

    @SerializedName("items")
    private List<T> items;

    public int getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }

}
