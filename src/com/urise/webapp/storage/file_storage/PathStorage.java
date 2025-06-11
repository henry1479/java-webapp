package com.urise.webapp.storage.file_storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.file_storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerializer serializer;


    protected PathStorage(String directory, StreamSerializer serializer) {
        Objects.requireNonNull(directory, "Directory must not be null");
        Path enterDirectory = Path.of(directory);
        if (!Files.isDirectory(enterDirectory)) {
            throw new IllegalArgumentException(enterDirectory.toAbsolutePath() + " Directory must  be directory");
        }

        if (!Files.isReadable(enterDirectory) || !Files.isReadable(enterDirectory)) {
            throw new IllegalArgumentException(enterDirectory.toAbsolutePath() + " Directory can not readable/writable");
        }

        this.directory = enterDirectory;
        this.serializer = serializer;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create new file " + path.toAbsolutePath(), r.getUuid(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", getFileNameFromPath(path), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", getFileNameFromPath(path), e);
        }
    }

    @Override
    protected List<Resume> getCopyStorage() {
        return getFileList()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFileList().forEach(this::doDelete);

    }

    @Override
    public int getSize() {
        return (int) getFileList().count();

    }


    private String getFileNameFromPath(Path path) {
        return path.getFileName().toString();
    }


    private Stream<Path> getFileList() {
        try {
            return Files.list(directory);
        } catch (IOException error) {
            throw new StorageException("Directory read error", null, error);

        }
    }


}
