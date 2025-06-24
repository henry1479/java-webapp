package com.urise.webapp;

import com.urise.webapp.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class MainFiles {

    private static final File PROPS = new File("./config/resumes.properties");
    private static final Properties props = new Properties();
    private static String dbUrl;

    public static void main(String[] args) {
        try (InputStream fis = new FileInputStream(PROPS)) {
            props.load(fis);

            dbUrl = props.getProperty("db.url");
            System.out.println(dbUrl);

        } catch (IOException error) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }

    }




    private static void directoryWalker(File directory) {
        for(File file : Objects.requireNonNull(directory.listFiles())) {
            if(!file.isDirectory()) {
                System.out.println("    " + file.getName());
            } else {
                System.out.println(file.getName());
                directoryWalker(file);
            }
        }
    }
}
