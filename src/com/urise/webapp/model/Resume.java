package com.urise.webapp.model;


import com.urise.webapp.model.section.Section;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * com.urise.webapp.model.Resume class
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Resume implements Comparable<Resume>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName, Map<ContactType, String> contacts,
                  Map<SectionType, Section> sections) {
        this(UUID.randomUUID().toString(), fullName, contacts, sections);
    }


    public Resume(String uuid,
                  String fullName,
                  Map<ContactType, String> contacts

    ) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
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
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Resume resume)) return false;
        return Objects.equals(uuid, resume.uuid)
                && Objects.equals(fullName, resume.fullName)
                && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections)

                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

}
