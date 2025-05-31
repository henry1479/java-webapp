package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage<Resume> {

    protected abstract boolean isExist(K searchKey);
    protected abstract K getSearchKey(String uuid);

    protected abstract void doSave(Resume r, K resumeKey);
    protected abstract void doUpdate(Resume r, K resumeKey);
    protected abstract void doDelete(K resumeKey);
    protected abstract Resume doGet(K resumeKey);
    protected abstract List<Resume> getCopyStorage();

    @Override
    public void save(Resume r) {
        K resumeKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, resumeKey);
    }

    @Override
    public void update(Resume r) {
        K resumeKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, resumeKey);

    }

    @Override
    public void delete(String uuid) {
        K resumeKey = getExistedSearchKey(uuid);
        doDelete(resumeKey);

    }

    @Override
    public Resume get(String uuid) {
        K resumeKey = getExistedSearchKey(uuid);
        return doGet(resumeKey);
    }

    private K getExistedSearchKey(String uuid) {
        K resumeIndex = getSearchKey(uuid);
        if (!isExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        return resumeIndex;
    }


    private K getNotExistedSearchKey(String uuid) {
        K resumeIndex = getSearchKey(uuid);
        if (isExist(resumeIndex)) {
            throw new ExistStorageException(uuid);
        }
        return resumeIndex;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = getCopyStorage();
        Collections.sort(result);
        return result;
    }
}


