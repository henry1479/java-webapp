package com.urise.webapp.storage.collection_storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> getCopyStorage() {
        return new ArrayList<>(map.values());
    }


    @Override
    public int getSize() {
        return map.size();
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume r, String resumeKey) {
        map.put(resumeKey, r);
    }

    @Override
    protected void doUpdate(Resume r, String resumeKey) {
        map.put(resumeKey, r);
    }

    @Override
    protected void doDelete(String resumeKey) {
        map.remove(resumeKey);
    }

    @Override
    protected Resume doGet(String resumeKey) {
        return map.get(resumeKey);
    }
}
