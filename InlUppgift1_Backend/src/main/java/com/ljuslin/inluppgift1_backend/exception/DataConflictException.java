package com.ljuslin.inluppgift1_backend.exception;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String txt) {
        super(txt);
    }
}
