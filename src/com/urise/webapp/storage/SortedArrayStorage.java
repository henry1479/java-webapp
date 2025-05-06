package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void insertElement(Resume r, int index) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = r;
    }

    protected int getIndex(String uuid) {
        Resume candidate = new Resume();
        candidate.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size,candidate);
    }

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if(numMoved > 0) {
            System.arraycopy(storage,index + 1, storage, index, numMoved);
        }
    }
}
