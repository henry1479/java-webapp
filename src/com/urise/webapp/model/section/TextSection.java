package com.urise.webapp.model.section;


import java.io.Serial;
import java.util.Objects;


public class TextSection extends Section {

    @Serial
    private static final long serialVersionUID = 1L;
    private String text;


    public String getText() {
        return text;
    }

    public TextSection(String text) {
        Objects.requireNonNull(text, "Text must not be null ");
        this.text = text;
    }

    public TextSection() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextSection that)) return false;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}';
    }
}
