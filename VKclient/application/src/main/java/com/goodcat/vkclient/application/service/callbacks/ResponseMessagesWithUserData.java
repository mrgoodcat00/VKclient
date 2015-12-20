package com.goodcat.vkclient.application.service.callbacks;

import java.util.List;

public interface ResponseMessagesWithUserData<T,U> {
    void onResponse(List<T> items, List<U> uIds);
}
