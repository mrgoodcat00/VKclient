package com.goodcat.vkclient.application.service.callbacks;


import java.util.List;

public interface ResponseHomeCallback<T,M,P,G> {

    void onResponse(List<T> items,List<M> wItems,List<P> wProfiles, List<G> wGroups);

}
