package com.ekipa.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ekipa.exception.ContentEmptyException;
import com.ekipa.exception.ContentNotAvailableException;
import com.ekipa.exception.InternalServerErrorException;
import com.ekipa.exception.PathNotAFileException;
import com.ekipa.exception.PermissionException;
import com.ekipa.model.ChildrenModel;
import com.ekipa.model.DirectoryModel;
import com.ekipa.model.FileModel;
import com.ekipa.service.FileService;
import com.ekipa.util.FileUtil;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Autowired
    private FileUtil fileUtil;

    @Override
    public byte[] getFileContent(String id) throws Exception {
        Path path = fileUtil.getPath(id);

        checkIfFileNotExists(path);

        if (fileUtil.isDirectory(path)) {
            throw new ContentNotAvailableException("The given path is a directory, the content is not available");
        }

        return fileUtil.readBytes(path);
    }

    @Override
    public void createDirectory(String id) throws FileAlreadyExistsException, InternalServerErrorException {
        Path path = fileUtil.getPath(id);

        if (fileUtil.fileExists(path)) {
            throw new FileAlreadyExistsException("Directory with the given id already exists. ID = " + id);
        }

        fileUtil.createDirectories(path);
    }

    @Override
    public FileModel findFile(String id) throws FileNotFoundException, InternalServerErrorException {
        Path path = fileUtil.getPath(id);

        checkIfFileNotExists(path);

        if (fileUtil.isDirectory(path)) {
            return DirectoryModel.createDirectory(id, path, fileUtil.getFileAttributes(path), getChildren(id, path));
        } else {
            return FileModel.createFile(id, path, fileUtil.getFileAttributes(path));
        }
    }

    @Override
    public void createFile(String id, MultipartFile file) throws FileAlreadyExistsException, InternalServerErrorException, ContentEmptyException {
        if (file == null) {
            throw new ContentEmptyException("File provided is empty");
        }
        try {
            createFile(id, file.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to write the file with id:" + id + ", please try again");
        }
    }

    @Override
    public void createFile(String id, byte[] content) throws FileAlreadyExistsException, InternalServerErrorException, ContentEmptyException {
        if (content == null || content.length == 0) {
            throw new ContentEmptyException("File provided is empty");
        }

        Path path = fileUtil.getPath(id);

        if (fileUtil.fileExists(path)) {
            throw new FileAlreadyExistsException("File with the given id already exists. ID = " + id);
        }
        fileUtil.createFile(path, content);
    }

    @Override
    public void updateFile(String id, MultipartFile file) throws Exception {
        if (file == null) {
            throw new ContentEmptyException("File provided is empty");
        }
        try {
            updateFile(id, file.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to update the file with id:" + id + ", please try again");
        }
    }

    @Override
    public void updateFile(String id, byte[] content) throws Exception {
        if (content == null || content.length == 0) {
            throw new ContentEmptyException("File provided is empty");
        }

        Path path = fileUtil.getPath(id);
        checkIfFileNotExists(path);

        if (fileUtil.isDirectory(path)) {
            throw new PathNotAFileException("Id provided is a directory, can not write into a directory");
        }

        checkWritePermission(path);

        fileUtil.updateFile(path, content);
    }

    @Override
    public void deleteFile(String id) throws Exception {
        Path path = fileUtil.getPath(id);

        checkIfFileNotExists(path);

        checkWritePermission(path);

        fileUtil.deleteFile(path);
    }

    private ChildrenModel getChildren(String id, Path path) throws InternalServerErrorException {
        return new ChildrenModel(getFileIdsInDirectory(id, path), getDirectoryIdsInDirectory(id, path));
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

    private void checkIfFileNotExists(Path path) throws FileNotFoundException {
        if (fileUtil.fileNotExists(path)) {
            throw new FileNotFoundException("File not found with the given id:" + path.toString());
        }
    }

    private void checkWritePermission(Path path) throws PermissionException {
        if (fileUtil.isNotWritable(path)) {
            throw new PermissionException("No permission to write in file with id:" + path.toString());
        }
    }
}