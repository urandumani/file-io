package com.ekipa.exception;

public class FileAlreadyExistsException extends Exception {

    public FileAlreadyExistsException(String msg) {
        super(msg);
    }
}