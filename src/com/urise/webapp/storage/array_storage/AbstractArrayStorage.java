package com.urise.webapp.storage.array_storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.Storage;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int INIT_CAPACITY = 1000;

    protected final Resume[] storage = new Resume[INIT_CAPACITY];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume r, Integer resumeIndex) {
       if(size == INIT_CAPACITY) {
            throw new StorageException("Storage is overflow!", r.getUuid());
        }
        else {
            insertElement(r, resumeIndex);
            size++;
        }
    }

    @Override
    public void doUpdate(Resume r, Integer resumeIndex) {
            storage[resumeIndex] = r;
    }

    @Override
    protected Resume doGet(Integer resumeIndex) {
        return storage[resumeIndex];
    }

    @Override
    protected void doDelete(Integer resumeIndex) {
            fillDeletedElement(resumeIndex);
            storage[size - 1] = null;
            size--;
    }


    @Override
    protected List<Resume> getCopyStorage() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0 ;
    }

    protected abstract Integer getSearchKey(String uuid);
    protected abstract void insertElement(Resume r, int index);
    protected abstract void fillDeletedElement(int index);
}
