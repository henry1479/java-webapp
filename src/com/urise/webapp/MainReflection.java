package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.util.ResumeFabric;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = ResumeFabric.generate("UUID_7");
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
    }
}
