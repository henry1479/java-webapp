package com.urise.webapp.storage.array_storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.util.ResumeFabric;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {


    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected void insertElement(Resume r, int index) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected Integer getSearchKey(String uuid) {

        Resume candidate = ResumeFabric.generate(uuid);
        return Arrays.binarySearch(storage, 0, size, candidate, RESUME_COMPARATOR);

    }

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }
}
