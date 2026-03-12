package com.ljuslin.inluppgift1_backend.exception;

public class DateOfBirthAlreadyExistsException extends RuntimeException {
    public DateOfBirthAlreadyExistsException() {
        super("Personnummer används redan av en annan användare");
    }
}
