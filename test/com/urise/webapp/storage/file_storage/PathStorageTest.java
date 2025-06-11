package com.urise.webapp.storage.file_storage;

import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.file_storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}
