package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private final static String UUID_4 = "uuid4";
    private final static Resume RESUME_4 = new Resume(UUID_4);

    private final static String UUID_1 = "uuid1";
    private final static Resume RESUME_1 = new Resume(UUID_1);

    private final static String UUID_2 = "uuid2";
    private final static Resume RESUME_2 = new Resume(UUID_2);

    private final static String UUID_5 = "uuid5";
    private final static Resume RESUME_5 = new Resume(UUID_5);


    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.save(RESUME_4);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_5);
        assertSize(4);
        assertGet(RESUME_5);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_4);

    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test
    public void getAllSorted() {
        List resumes = storage.getAllSorted();

        assertEquals(3, resumes.size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_4), resumes);

    }

    @Test
    public void getSize() {
        assertSize(3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_5);
    }


    private void assertSize(int size) {
        assertEquals(size, storage.getSize());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

}
