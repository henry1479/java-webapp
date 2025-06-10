package com.urise.webapp.storage.file_storage;

import com.urise.webapp.storage.AbstractStorageTest;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new ObjectFileStorage(STORAGE_DIR));
    }
}
