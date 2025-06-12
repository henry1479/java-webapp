package com.urise.webapp.model;


import com.urise.webapp.model.section.Section;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * com.urise.webapp.model.Resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private final String uuid;
    private final String fullName;


    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);


    public Resume(String fullName, Map<ContactType, String> contacts,
                  Map<SectionType, Section> sections) {
        this(UUID.randomUUID().toString(), fullName, contacts, sections);
    }

    public Resume(String uuid,
                  String fullName,
                  Map<ContactType, String> contacts,
                  Map<SectionType, Section> sections
    ) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
        this.sections = sections;
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        return fullName.compareTo(o.fullName);
    }

    public String getUuid() {
        return uuid;
    }


    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSections(SectionType type) {
        return sections.get(type);
    }

}
