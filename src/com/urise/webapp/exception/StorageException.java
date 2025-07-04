package com.urise.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;
    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception error) {
        this(message, null, error);
    }
    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message) {
        this(message, null, null);
    }
}
