package com.urise.webapp.model;

import java.util.UUID;

/**
 * com.urise.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume>{

    // Unique identifier
    private final String uuid;

    public Resume () {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Resume resume)) {
            return false;
        }

        return this.uuid.equals(resume.uuid);
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }



    public String getUuid() {
        return uuid;
    }
}
