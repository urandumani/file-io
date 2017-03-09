package com.ekipa.exception.handler;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ekipa.exception.ContentEmptyException;
import com.ekipa.exception.ContentNotAvailableException;
import com.ekipa.exception.FileAlreadyInUseException;
import com.ekipa.exception.PathNotAFileException;
import com.ekipa.exception.PermissionException;
import com.ekipa.model.MessageModel;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageModel notFound(FileNotFoundException e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(ContentNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageModel contentNotAvailable(ContentNotAvailableException e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageModel fileAlreadyExists(FileAlreadyExistsException e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(FileAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageModel fileAlreadyInUse(FileAlreadyInUseException e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageModel noPermission(PermissionException e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(PathNotAFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageModel pathNotAFile(PathNotAFileException e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageModel generic(Exception e) {
        return MessageModel.message(e.getMessage());
    }

    @ExceptionHandler(ContentEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageModel contentEmpty(ContentEmptyException e) {
        return MessageModel.message(e.getMessage());
    }
}
