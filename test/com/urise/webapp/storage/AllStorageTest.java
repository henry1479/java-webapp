package com.urise.webapp.storage;


import com.urise.webapp.storage.array_storage.ArrayStorageTest;
import com.urise.webapp.storage.array_storage.SortedArrayStorageTest;
import com.urise.webapp.storage.collection_storage.ListStorageTest;
import com.urise.webapp.storage.collection_storage.MapResumeStorage;
import com.urise.webapp.storage.collection_storage.MapResumeStorageTest;
import com.urise.webapp.storage.collection_storage.MapUuidStorageTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({

        ListStorageTest.class,
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class
})
public class AllStorageTest {
}
