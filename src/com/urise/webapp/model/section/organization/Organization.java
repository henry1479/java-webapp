package com.urise.webapp.model.section.organization;

import com.urise.webapp.model.section.util.Link;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final Link homePage;
    private final List<Position> positions;




    public Organization(String name, String url, List<Position> positions) {
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    public Organization(String name, String url, Position ...positions) {
        this(name, url, List.of(positions));
    }


    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Organization that)) return false;
        return Objects.equals(homePage, that.homePage) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }
}
