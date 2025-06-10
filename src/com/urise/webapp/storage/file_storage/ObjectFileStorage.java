package com.urise.webapp.storage.file_storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectFileStorage extends AbstractFileStorage{

    protected ObjectFileStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream stream) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(stream)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream stream) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(stream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException error) {
            throw new StorageException("Read file error", "", error);
        }
    }
}
