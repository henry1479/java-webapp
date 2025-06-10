package com.urise.webapp.storage.array_storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.storage.util.ResumeFabric;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {


    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.INIT_CAPACITY; i++) {
                storage.save(ResumeFabric.generate("UUID_" + i));
            }
        } catch (StorageException e) {
            Assert.fail();
        }

        storage.save(ResumeFabric.generate("Overflow"));
    }




}