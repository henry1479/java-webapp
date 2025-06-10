package com.urise.webapp.storage.file_storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " Directory must be directory");
        }

        if (!directory.canRead() || !directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " Directory can not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume r, OutputStream stream) throws IOException;
    protected abstract Resume doRead(InputStream stream) throws IOException;

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create new file " + file.getAbsolutePath(), r.getUuid(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if(!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getCopyStorage() {
        File[] fileList = directory.listFiles();
        if(fileList == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> result = new ArrayList<>(fileList.length);
        for(File file : fileList) {
            result.add(doGet(file));
        }
        return result;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if(listFiles != null) {
            for (File file : listFiles) {
                doDelete(file);
            }
        }

    }

    @Override
    public int getSize() {
        String[] list = directory.list();
        if(list == null) {
            throw new StorageException("Directory read error", null);
        }

        return list.length;
    }


}
