package com.urise.webapp.model;

/**
 * com.urise.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume>{

    // Unique identifier
    private String uuid;

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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
