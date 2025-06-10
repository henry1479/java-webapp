package com.urise.webapp.model.section.organization;

import com.urise.webapp.util.DateUtil;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;

public class Position  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "StartDate must not be null");
        Objects.requireNonNull(endDate, "EndDate must not be null");
        Objects.requireNonNull(title, "Title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description == null ? "" : description;
    }

    public Position(int startYear, Month startMonth, String title, String description) {
        this(DateUtil.of(startYear, startMonth), NOW, title, description);
    }

    public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;
        return Objects.equals(startDate, position.startDate) && Objects.equals(endDate, position.endDate) && Objects.equals(title, position.title) && Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }
}
