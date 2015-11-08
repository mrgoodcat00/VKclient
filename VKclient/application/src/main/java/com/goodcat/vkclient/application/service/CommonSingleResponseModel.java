package com.goodcat.vkclient.application.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Goodcat on 06.11.2015.
 */
public class CommonSingleResponseModel<T> {

    @SerializedName("response")
    private T response;

    public T getResponse() {
        return response;
    }


}
