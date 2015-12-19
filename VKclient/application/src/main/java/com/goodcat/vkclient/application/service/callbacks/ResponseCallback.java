package com.goodcat.vkclient.application.service.callbacks;


import java.util.List;

public interface ResponseCallback<T> {
    void onResponse(List<T> items);
}
