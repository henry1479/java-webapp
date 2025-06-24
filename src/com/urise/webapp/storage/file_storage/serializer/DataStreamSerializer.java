package com.urise.webapp.storage.file_storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.section.ListSection;
import com.urise.webapp.model.section.TextSection;
import com.urise.webapp.model.section.organization.Organization;
import com.urise.webapp.model.section.organization.OrganizationSection;
import com.urise.webapp.model.section.organization.Position;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataStreamSerializer implements StreamSerializer {

//    private List<String> strings = new ArrayList<>();
//    private List<Position> positions = new ArrayList<>();
//    private List<Organization> organizations = new ArrayList<>();


    @Override
    public void doWrite(Resume r, OutputStream stream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(stream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            dos.writeInt(r.getContacts().size());
            r.getContacts().forEach((type, contact) -> {
                try {
                    dos.writeUTF(type.name());
                    dos.writeUTF(contact);
                } catch (IOException e) {
                    throw new StorageException(e);
                }
            });
            int sectionsSize = r.getSections().size();
            dos.writeInt(sectionsSize);
            r.getSections().forEach((type, value) -> {
                try {
                    dos.writeUTF(type.name());
                    dos.writeUTF(value.getClass().toString());
                    if (value instanceof TextSection) {
                        dos.writeUTF(((TextSection) value).getText());
                    } else if (value instanceof ListSection) {
                        dos.writeInt(((ListSection) value).getList().size());
                        for (String item : ((ListSection) value).getList()) {
                            dos.writeUTF(item);
                        }
                    } else {
                        List<Organization> organizations = ((OrganizationSection) value).getList();
                        dos.writeInt(organizations.size());
                        for (Organization org : organizations) {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            List<Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Position position : positions) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new StorageException(e);
                }

            });
        }
    }

    @Override
    public Resume doRead(InputStream stream) throws IOException {
        try (DataInputStream dis = new DataInputStream(stream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            int contactsSize = dis.readInt();
            Resume resume = new Resume(uuid, fullName);

            Stream.iterate(0, i -> i + 1)
                    .limit(contactsSize)
                    .forEach(i -> {
                        try {
                            makeDependsOnContext("contact", resume, dis.readUTF(), dis.readUTF());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        };
                    });
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                String sectionName = dis.readUTF();
                if (sectionName.contains("TextSection")) {
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));
                } else if (sectionName.contains("ListSection")) {
                    int listSize = dis.readInt();
                    List<String> list = new ArrayList<>(listSize);
                    for (int j = 0; j < listSize; j++) {
                        list.add(dis.readUTF());
                    }
                    resume.addSection(sectionType, new ListSection(list));
                } else if (sectionName.contains("OrganizationSection")) {
                    int organizationsSize = dis.readInt();
                    List<Organization> organizations = new ArrayList<>();
                    for (int j = 0; j < organizationsSize; j++) {
                        String name = dis.readUTF();
                        String url = dis.readUTF();
                        List<Position> positions = new ArrayList<>();
                        int positionsSize = dis.readInt();
                        for (int k = 0; k < positionsSize; k++) {
                            positions.add(new Position(
                                    LocalDate.parse(dis.readUTF()),
                                    LocalDate.parse(dis.readUTF()),
                                    dis.readUTF(),
                                    dis.readUTF()
                            ));
                        }
                        organizations.add(new Organization(name, url, positions));
                    }
                    resume.addSection(sectionType, new OrganizationSection(organizations));
                }

            }

            return resume;
        }
    }

    private Resume makeDependsOnContext(String context, Resume r, String type, String value )  {
        if(context.equals("contacts")) {
            r.addContact(ContactType.valueOf(type), value);
        }
        return r;
    }

}
