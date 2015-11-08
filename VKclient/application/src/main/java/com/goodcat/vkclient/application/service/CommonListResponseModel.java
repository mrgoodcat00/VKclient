package com.goodcat.vkclient.application.service;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonListResponseModel<T> {

    @SerializedName("response")
    private List<T> response;

    public List<T> getResponse() {
        return response;
    }

}
