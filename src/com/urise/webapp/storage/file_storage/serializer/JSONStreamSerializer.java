package com.urise.webapp.storage.file_storage.serializer;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JSONStreamSerializer implements StreamSerializer {

    JSONParser parser = new JSONParser();

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try(Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            parser.write(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
             return parser.read(reader, Resume.class);
        }
    }
}
