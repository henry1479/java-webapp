package com.urise.webapp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final File propsFile = new File("./config/resumes.properties");
    private final Properties props = new Properties();
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private File storageDir;

    public static Config get() {
        return INSTANCE;
    }


    private Config() {
        try (InputStream fis = new FileInputStream(propsFile);
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)
        ) {
            props.load(reader);

            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");

        } catch (IOException error) {
            throw new IllegalStateException("Invalid config file " + propsFile.getAbsolutePath());
        }
    }


    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
