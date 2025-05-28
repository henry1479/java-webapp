package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException{

    public NotExistStorageException(String uuid) {
        super("This resume not exists in storage " + uuid, uuid);
    }
}
