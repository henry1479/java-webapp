package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Storage<Resume> sqlStorage;
    private final File storageDir;

    public static Config get() {
        return INSTANCE;
    }


    private Config() {
        File propsFile = new File("/home/henry1479/IdeaProjects/java-webapp/config/resumes.properties");
        try (InputStream fis = new FileInputStream(propsFile);
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)
        ) {
            Properties props = new Properties();
            props.load(reader);
            storageDir = new File(props.getProperty("storage.dir"));

            sqlStorage = new SqlStorage(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );


        } catch (IOException error) {
            throw new IllegalStateException("Invalid config file " + propsFile.getAbsolutePath());
        }
    }


    public File getStorageDir() {
        return storageDir;
    }

    public Storage<Resume> getSqlStorage() {
        return sqlStorage;
    }
}
