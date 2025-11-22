package com.example.authservice.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserErrorResponse> handleNotFound(UserAlreadyExistsException ex) {
        return ResponseEntity.badRequest()
                .body(new UserErrorResponse(HttpStatus.NOT_FOUND.value(), "User already exists"));
    }

    private record UserErrorResponse(
            int errorCode,
            String message
    ) {
    }
}
