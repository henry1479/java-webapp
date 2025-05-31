package com.urise.webapp.storage.collection_storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    protected List<Resume> getCopyStorage() {
        return new ArrayList<>(list);
    }

    protected boolean isExist(Integer searchKey) {
       return searchKey != null;
    }


    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < getSize(); i++) {
            if(list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doSave(Resume r, Integer resumeKey) {
        list.add(r);
    }

    @Override
    protected void doUpdate(Resume r, Integer resumeKey) {
        list.set(resumeKey, r);
    }

    @Override
    protected void doDelete(Integer resumeKey) {
        list.remove(resumeKey.intValue());
    }

    @Override
    protected Resume doGet(Integer resumeKey) {
        return list.get(resumeKey);
    }
}
