package com.ekipa.service;

import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.ekipa.exception.ContentEmptyException;
import com.ekipa.exception.FileAlreadyExistsException;
import com.ekipa.exception.InternalServerErrorException;
import com.ekipa.model.FileModel;

public interface FileService {

    byte[] getFileContent(String id) throws Exception;

    void createDirectory(String id) throws FileAlreadyExistsException, InternalServerErrorException;

    FileModel findFile(String id) throws FileNotFoundException, InternalServerErrorException;

    void createFile(String id, MultipartFile file) throws FileAlreadyExistsException, InternalServerErrorException, ContentEmptyException;

    void createFile(String id, byte[] content) throws FileAlreadyExistsException, InternalServerErrorException, ContentEmptyException;

    void updateFile(String id, MultipartFile file) throws Exception;

    void updateFile(String id, byte[] content) throws Exception;

    void deleteFile(String id) throws Exception;
}