package com.ljuslin.inluppgift1_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleNotFound(MemberNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timeStamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", "Not Found",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(IllegalIdException.class)
    public ResponseEntity<?> handleIllegalId(IllegalIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "timeStamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", "Bad request",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(DateOfBirthAlreadyExistsException.class)
    public ResponseEntity<?> handleIllegalDateOfBirth(DateOfBirthAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timeStamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", "Conflict",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleIllegalUsername(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timeStamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", "Conflict",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(IllegalPasswordException.class)
    public ResponseEntity<?> handleIllegalUsername(IllegalPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "timeStamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", "Bad request",
                        "message", ex.getMessage()
                )
        );
    }
}

