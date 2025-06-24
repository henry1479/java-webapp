package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage<Resume>{
    private final ConnectionFactory connection;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connection = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes ")
        ) {
            ps.execute();
        } catch (SQLException error) {
            throw new StorageException(error);
        }

    }

    @Override
    public void save(Resume r) {

        try (Connection conn = connection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes (uuid, full_name) VALUES (?,?)")
        ) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException error) {
            throw new StorageException(error);
        }

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes r WHERE r.uuid = ? ")
        ) {
            ps.setString(1, uuid);
            ResultSet rs = ps.getResultSet();
            if(!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException error) {
            throw new StorageException(error);
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List getAllSorted() {
        return List.of();
    }

    @Override
    public int getSize() {
        return 0;
    }
}
