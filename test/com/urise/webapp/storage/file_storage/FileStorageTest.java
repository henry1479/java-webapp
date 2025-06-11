package com.urise.webapp.storage.file_storage;

import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.file_storage.serializer.ObjectStreamSerializer;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
