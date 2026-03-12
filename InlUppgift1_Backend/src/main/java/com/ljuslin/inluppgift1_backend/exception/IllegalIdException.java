package com.ljuslin.inluppgift1_backend.exception;

public class IllegalIdException extends RuntimeException {
    public IllegalIdException(String id) {
        super(id + " är inte ett giltigt id");
    }
}
