package com.ljuslin.inluppgift1_backend.exception;

public class IllegalPasswordException extends RuntimeException {
    public IllegalPasswordException(String pw) {
        super(pw + " är inte ett giltigt password");
    }
}
