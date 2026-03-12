package com.ljuslin.inluppgift1_backend.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Personnummer används redan av en annan användare");
    }
}
