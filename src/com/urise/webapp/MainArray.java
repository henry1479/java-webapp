package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.array_storage.ArrayStorage;
import com.urise.webapp.storage.array_storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

/**
 *
 * Test for com.urise.webapp.storage.array_storage.ArrayStorage
 */
public class MainArray {
    private static final Storage arrayStorage = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        arrayStorage.save(r3);
        arrayStorage.save(r1);
        arrayStorage.save(r2);

        System.out.println("Get r1: " + arrayStorage.get(r1.getUuid()));
        System.out.println("Size: " + arrayStorage.getSize());

        printAll();
        arrayStorage.delete(r1.getUuid());
        printAll();
        arrayStorage.clear();
        printAll();

        System.out.println("Size: " + arrayStorage.getSize());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Object r : arrayStorage.getAllSorted()) {
            System.out.println(r);
        }
    }
}
