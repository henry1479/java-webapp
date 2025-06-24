package com.urise.webapp.model.section;


import java.io.Serial;
import java.util.List;
import java.util.Objects;


public class ListSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<String> list;


    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "List must not be null");
        this.list = list;
    }

    public ListSection() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ListSection that)) return false;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(list);
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + list +
                '}';
    }
}
