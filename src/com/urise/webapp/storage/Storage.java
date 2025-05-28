package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage <T>{

    void clear();

    void save(T r);

    void update(T r);

    Resume get(String uuid);

    void delete(String uuid);

    List<T> getAllSorted();

    int getSize();

}
