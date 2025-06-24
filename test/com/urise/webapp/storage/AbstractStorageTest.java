package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.util.ResumeFabric;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {

    protected final static File STORAGE_DIR = new File("/home/henry1479/Рабочий стол/Junior Java-разработчик веб-приложений - BaseJava/code/java-webapp/src/storage");

    protected Storage storage;


    private final static Resume RESUME_4 = ResumeFabric.generate("UUID_4");

    private final static Resume RESUME_1 = ResumeFabric.generate("UUID_1");

    private final static Resume RESUME_2 = ResumeFabric.generate("UUID_2");

    private final static Resume RESUME_3 = ResumeFabric.generate("UUID_3");


    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {

        storage.clear();
        storage.save(RESUME_3);
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
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void update() {
        Resume newResume = ResumeFabric.generate("UUID_2");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get("UUID_2")));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);

    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        storage.get(RESUME_1.getUuid());
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();

        assertEquals(3, resumes.size());
        List<Resume> expected = Arrays.asList(RESUME_3, RESUME_2, RESUME_1);
        expected.sort(Comparator.comparing(Resume::getFullName));
        assertEquals(expected, resumes);

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
        storage.update(RESUME_4);
    }


    private void assertSize(int size) {
        assertEquals(size, storage.getSize());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

}
