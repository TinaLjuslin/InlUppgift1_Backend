package com.ljuslin.inluppgift1_backend.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long id) {
        super("Medlem med id " + id + " hittades inte");
    }
}
