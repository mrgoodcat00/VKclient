package com.goodcat.vkclient.application.service;


import java.util.List;

public interface ResponseHomeCallback<T,M> {

    void onResponse(List<T> items,List<M> wItems);

}
