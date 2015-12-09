package com.goodcat.vkclient.application.db.dao;

import java.util.List;

public interface CommonDAO<T> {

    boolean insert(T item);

    boolean update(T item);

    boolean delete(T item);

    T findById(long id);

    List<T> loadAll() ;

}
