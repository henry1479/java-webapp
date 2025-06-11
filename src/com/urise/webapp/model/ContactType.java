package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    MOBILE_PHONE("Мобильный телефон"),
    HOME_PHONE("Домашний телефон"),
    EMAIL("Электронная почта"),
    SKYPE("Skype"),
    LINKEDIN("Linked in"),
    GITHUB("Github"),
    HOME_PAGE("Домашняя страница"),
    STACKOVERFLOW("Stack Overflow");


    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
