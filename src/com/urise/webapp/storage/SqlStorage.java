package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage<Resume> {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String username, String password) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, username, password));
    }

    @Override
    public void clear() {
        sqlHelper.<Void>execute("DELETE from contacts", ps -> {
            ps.execute();
            return null;
        });
        sqlHelper.<Void>execute("DELETE from resumes", ps -> {
            ps.execute();
            return null;
        });

    }

    @Override
    public void save(Resume r) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes(uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new ExistStorageException(r.getUuid());
                }
            }
            insertContacts(conn, r);
            return null;
        });


    }

    @Override
    public void update(Resume r) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resumes SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContacts(conn, r);

            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("""
                 SELECT * FROM resumes r\s
                 LEFT JOIN contacts c
                 ON r.uuid = c.resume_uuid\s
                 WHERE uuid=?
                \s""", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(rs, r);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resumes WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(""" 
                SELECT * FROM resumes r
                LEFT JOIN contacts c
                ON r.uuid = c.resume_uuid\s
                ORDER BY full_name, uuid
                """, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> resumesMap = new HashMap<>();

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume r = resumesMap.get(uuid);
                if (r == null) {
                    r = new Resume(uuid, rs.getString("full_name"));
                    resumesMap.put(uuid, r);
                }
                addContact(rs, r);

            }
            return new ArrayList<>(resumesMap.values());
        });
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resumes", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }


    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contacts WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        ContactType cp = ContactType.valueOf(rs.getString("type"));
        r.addContact(cp, value);
    }
}
