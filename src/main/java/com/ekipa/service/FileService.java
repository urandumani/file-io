package com.ekipa.service;

import com.ekipa.model.Children;
import com.ekipa.model.Directory;
import com.ekipa.model.File;
import com.ekipa.exception.InternalServerErrorException;
import com.ekipa.util.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileService {
    private static final Logger logger = Logger.getLogger(FileService.class);

    @Autowired
    private FileUtil fileUtil;

    public File findFile(String id) throws FileNotFoundException, InternalServerErrorException {
        Path path = fileUtil.getPath(id);

        if (fileUtil.fileNotExists(path)) {
            throw new FileNotFoundException("The item with given ID could not be found on filesystem.");
        }
        if (Files.isDirectory(path)) {
            return Directory.createDirectory(id, path, fileUtil.getFileAttributes(path), getChildren(id, path));
        } else {
            return File.createFile(id, path, fileUtil.getFileAttributes(path));
        }
    }

    private Children getChildren(String id, Path path) throws InternalServerErrorException {
        return new Children(getFileIdsInDirectory(id, path), getDirectoryIdsInDirectory(id, path));
    }

    private Set<String> getFileIdsInDirectory(String id, Path path) throws InternalServerErrorException {
        return getIdsInDirectory(fileUtil.getFiles(path), id);
    }

    private Set<String> getDirectoryIdsInDirectory(String id, Path path) throws InternalServerErrorException {
        return getIdsInDirectory(fileUtil.getDirectories(path), id);
    }

    private Set<String> getIdsInDirectory(Set<Path> paths, String id) {
        Set<String> ids = new HashSet<>();
        for (Path p : paths) {
            ids.add(id + "/" + p.getFileName().toString());
        }
        return ids;
    }

    public byte[] fileContent(String id) throws InternalServerErrorException, FileNotFoundException {
        Path path = fileUtil.getPath(id);

        if (fileUtil.fileNotExists(path)) {
            throw new FileNotFoundException("The ID could not be found on filesystem.");
        }

        if (Files.isDirectory(path)) {
            throw new InternalServerErrorException("The file content requested is not allowed. Refer to the response details regarding validation errors.");
        }

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Internal server error.");
        }
    }
}
