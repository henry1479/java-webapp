package com.urise.webapp.storage.util;


import com.github.javafaker.Faker;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.section.ListSection;
import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.TextSection;
import com.urise.webapp.model.section.organization.Organization;
import com.urise.webapp.model.section.organization.OrganizationSection;
import com.urise.webapp.model.section.organization.Position;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ResumeFabric {

    private static final Faker faker = new Faker(new Locale("ru", "Ru"));

    public static Resume generate() {
        return new Resume(
                getUuid(),
                getFullName(),
                getContacts(),
                getSections()
        );
    }


    public static Resume generate(String uuid) {
        return new Resume(
                uuid,
                getFullName(),
                getContacts(),
                getSections()
        );
    }

    public static Resume generate(String uuid, String fullName) {
        return new Resume(
                uuid,
                fullName,
                getContacts(),
                getSections()
        );
    }


    private static String getUuid() {
        return UUID.randomUUID().toString();
    }

    private static String getCustomUuid(int suffix) {
        return "UUID_" + String.valueOf(suffix);
    }

    private static String getFullName() {
        return faker.name().fullName();
    }

    private static Map<ContactType, String> getContacts() {
        Map<ContactType, String> result = new EnumMap<>(ContactType.class) {{
            put(ContactType.PHONE, faker.phoneNumber().phoneNumber());
            put(ContactType.HOME_PHONE, faker.phoneNumber().phoneNumber());
            put(ContactType.MOBILE_PHONE, faker.phoneNumber().cellPhone());
            put(ContactType.EMAIL, faker.bothify("\"????##@gmail.com\""));
            put(ContactType.GITHUB, faker.bothify("\"www.????##.github.com\""));
            put(ContactType.LINKEDIN, faker.bothify("\"www.????##linkedIn.com\""));
            put(ContactType.STACKOVERFLOW, faker.bothify("\"www.????####.stackOverflow.com\""));
            put(ContactType.HOME_PAGE, faker.bothify("\"www.????##???.com\""));
        }};
        return result;

    }


    private static Map<SectionType, Section> getSections() {
        return new EnumMap<>(SectionType.class) {{
            put(SectionType.OBJECTIVE, getTextSection(100));
            put(SectionType.PERSONAL, getTextSection(100));
            put(SectionType.ACHIEVEMENT, getListSection(4));
            put(SectionType.QUALIFICATIONS, getListSection(3));
            put(SectionType.EDUCATION, getOrganizationSection(2));
            put(SectionType.EXPERIENCE, getOrganizationSection(2));


        }};

    }

    private static Section getTextSection(int numberOfCharacters) {
        return new TextSection(faker.lorem().characters(numberOfCharacters, true));
    }

    private static Section getListSection(int numberOfItems) {

        List<String> result = new ArrayList<>() {{
            for (int i = 0; i < numberOfItems; i++) {
                add(faker.lorem().characters(24, true));
            }
        }};
        return new ListSection(result);
    }

    private static Section getOrganizationSection(int numberOfOrganizations) {
        List<Organization> result = new ArrayList<>() {{
            for (int i = 0; i < numberOfOrganizations; i++) {
                add(getOrganization());
            }
        }};
        return new OrganizationSection(result);
    }

    private static Organization getOrganization() {
        return new Organization(
                faker.company().name(),
                faker.company().url(),
                getPositions(4)
        );
    }


    private static List<Position> getPositions(int numberOfPositions) {
        List<Position> result = new ArrayList<>() {{
          add(getPosition());
          add(getPosition());
          add(getPosition());
          add(getPosition());
          add(getPosition());
        }};

        return result;
    }


    private static Position getPosition() {
        return new Position(
                DateUtil.of(faker.random().nextInt(13, 25), Month.of(faker.random().nextInt(1, 12))),
                DateUtil.of(faker.random().nextInt(13, 25), Month.of(faker.random().nextInt(1, 12))),
                faker.company().buzzword(),
                faker.company().industry()
        );

    }

}
