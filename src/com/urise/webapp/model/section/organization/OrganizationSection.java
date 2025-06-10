package com.urise.webapp.model.section.organization;

import com.urise.webapp.model.section.Section;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {

    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Organization> list;

    public List<Organization> getList() {
        return list;
    }

    public OrganizationSection(List<Organization> list) {
        Objects.requireNonNull(list, "List must not be null");
        this.list = list;
    }

    public OrganizationSection(Organization... organizations) {
        this(List.of(organizations));
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrganizationSection that)) return false;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(list);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "list=" + list +
                '}';
    }
}
