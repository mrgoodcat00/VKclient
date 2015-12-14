package com.goodcat.vkclient.application.service;


import java.util.List;

public interface ResponseLazyLoad<T,M,P,G> {
    void onResponse(List<T> items,List<M> wItems,List<P> wProfiles, List<G> wGroups);
}
