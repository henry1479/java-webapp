package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<K> implements Storage<Resume> {

    private final static Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract boolean isExist(K searchKey);
    protected abstract K getSearchKey(String uuid);
    protected abstract void doSave(Resume r, K resumeKey);
    protected abstract void doUpdate(Resume r, K resumeKey);
    protected abstract void doDelete(K resumeKey);
    protected abstract Resume doGet(K resumeKey);
    protected abstract List<Resume> getCopyStorage();

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        K resumeKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, resumeKey);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        K resumeKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, resumeKey);

    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete resume with uuid " + uuid);

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
            LOG.warning("The resume with uuid " + uuid +
                    " does not exist");
            throw new NotExistStorageException(uuid);
        }
        return resumeIndex;
    }


    private K getNotExistedSearchKey(String uuid) {
        K resumeIndex = getSearchKey(uuid);
        if (isExist(resumeIndex)) {
            LOG.warning("The resume with uuid " + uuid +
                    " exist");
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


