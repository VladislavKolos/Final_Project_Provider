package org.example.exception.handler;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ProviderBannedException;
import org.example.exception.ProviderConflictException;
import org.example.exception.ProviderNotFoundException;
import org.example.exception.ProviderTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class WebAppExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: " + e);
        return new ResponseEntity<>("RuntimeException occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException: " + e);
        return new ResponseEntity<>("NullPointerException occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        log.error("IOException: " + e);
        return new ResponseEntity<>("IOException occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException e) {
        log.error("ServletException: " + e);
        return new ResponseEntity<>("ServletException occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException" + e);
        return new ResponseEntity<>("IllegalArgumentException occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProviderBannedException.class)
    public ResponseEntity<String> handleProviderBannedException(ProviderBannedException e) {
        log.error("ProviderBannedException" + e);
        return new ResponseEntity<>("ProviderBannedException occurred: " + e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ProviderTokenException.class)
    public ResponseEntity<String> handleProviderTokenException(ProviderTokenException e) {
        log.error("ProviderTokenException" + e);
        return new ResponseEntity<>("ProviderTokenException occurred: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFoundException(ProviderNotFoundException e) {
        log.error("ProviderNotFoundException" + e);
        return new ResponseEntity<>("ProviderNotFoundException occurred: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProviderConflictException.class)
    public ResponseEntity<String> handleProviderConflictException(ProviderConflictException e) {
        log.error("ProviderConflictException" + e);
        return new ResponseEntity<>("ProviderConflictException occurred: " + e.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnotherException(Exception e) {
        log.error("Exception: " + e);
        return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
