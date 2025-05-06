package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage{

    private static final int INIT_CAPACITY = 1000;
    protected final Resume[] storage = new Resume[INIT_CAPACITY];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int resumeIndex = getIndex(r.getUuid());
        if(resumeIndex >= 0) {
            System.out.println("This resume is exist " + r.getUuid());
        } else if(size == INIT_CAPACITY) {
            System.out.println("The storage is overflow");
        }
        else {
            insertElement(r, resumeIndex);
            size++;
        }
    }

    @Override
    public void update(Resume r) {
        int resumeIndex = getIndex(r.getUuid());
        if(resumeIndex < 0) {
            System.out.println("This resume is not exist " + r.getUuid());
        } else {
            storage[resumeIndex] = r;
        }
    }

    @Override
    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if(resumeIndex < 0) {
            System.out.println("This resume is not exist  " + uuid);
            return null;
        }
        return storage[resumeIndex];
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if(resumeIndex < 0) {
            System.out.println("This resume is not exist " + uuid);
        } else {
            fillDeletedElement(resumeIndex);
            storage[size - 1] = null;
            size--;
        }
    }


    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int getSize() {
        return size;
    }

    protected abstract int getIndex(String uuid);
    protected abstract void insertElement(Resume r, int index);
    protected abstract void fillDeletedElement(int index);




}
