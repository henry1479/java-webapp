package com.urise.webapp.storage.file_storage.serializer;

import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.ListSection;
import com.urise.webapp.model.section.TextSection;
import com.urise.webapp.model.section.organization.Organization;
import com.urise.webapp.model.section.organization.OrganizationSection;
import com.urise.webapp.model.section.organization.Position;
import com.urise.webapp.model.section.util.Link;
import com.urise.webapp.util.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLStreamSerializer implements StreamSerializer {

    private final XMLParser parser;

    public XMLStreamSerializer() {
        this.parser = new XMLParser(Resume.class, Organization.class, Link.class,
                OrganizationSection.class, TextSection.class, ListSection.class, Position.class);
    }


    @Override
    public void doWrite(Resume r, OutputStream stream) throws IOException {
        try (Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            parser.marshal(r, writer);
        }

    }

    @Override
    public Resume doRead(InputStream stream) throws IOException {
        try (Reader r = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return parser.unmarshal(r);
        }

    }
}
