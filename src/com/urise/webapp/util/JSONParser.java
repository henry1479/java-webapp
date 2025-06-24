package com.urise.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.section.Section;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JSONParser {

    private final static Gson parser = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new GSONLocalDateAdapter())
            .registerTypeAdapter(Section.class, new GSONSectionAdapter<>())
            .create();

    public static <T> T read(Reader reader, Class<T> resourceClass) {
        return parser.fromJson(reader, resourceClass);
    }

    public <T> void write(T object, Writer writer) {
        parser.toJson(object, writer);
    }
}
