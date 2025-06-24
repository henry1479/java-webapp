package com.urise.webapp.storage;

import com.urise.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static final Config CONFIG = Config.get();

    public SqlStorageTest() {
        super(new SqlStorage(CONFIG.getDbUrl(), CONFIG.getDbUser(), CONFIG.getDbPassword()));
    }
}
