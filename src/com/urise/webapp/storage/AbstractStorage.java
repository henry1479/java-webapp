package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<K> implements Storage<Resume> {

    protected abstract boolean isExist(K searchKey);
    protected abstract K getIndex(String uuid);
    protected abstract void doSave(Resume r, K resumeKey);
    protected abstract void doUpdate(Resume r, K resumeKey);
    protected abstract void doDelete(K resumeKey);

    @Override
    public void save(Resume r) {
        K resumeKey = getNotExistedKey(r.getUuid());
        doSave(r, resumeKey);

    }

    @Override
    public void update(Resume r) {
        K resumeKey = getExistedKey(r.getUuid());
        doUpdate(r, resumeKey);

    }



    @Override
    public void delete(String uuid) {
        K resumeKey = getExistedKey(uuid);
        doDelete(resumeKey);

    }

    private K getExistedKey(String uuid) {
        K resumeIndex = getIndex(uuid);
        if (!isExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        return resumeIndex;
    }


    private K getNotExistedKey(String uuid) {
        K resumeIndex = getIndex(uuid);
        if (isExist(resumeIndex)) {
            throw new ExistStorageException(uuid);
        }
        return resumeIndex;
    }

}


