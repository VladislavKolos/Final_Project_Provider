package org.example.exception.handler;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * Global exception handler for the Provider project.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ProviderExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.runtime",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.null_pointer",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        log.error("IOException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.io",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException e) {
        log.error("ServletException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.servlet",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.illegal_argument",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProviderAccessDeniedException.class)
    public ResponseEntity<String> handleProviderAccessDeniedException(ProviderAccessDeniedException e) {
        log.error("ProviderAccessDeniedException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.access_denied",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ProviderBannedException.class)
    public ResponseEntity<String> handleProviderBannedException(ProviderBannedException e) {
        log.error("ProviderBannedException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.banned_provider",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ProviderTokenException.class)
    public ResponseEntity<String> handleProviderTokenException(ProviderTokenException e) {
        log.error("ProviderTokenException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.token_exception",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFoundException(ProviderNotFoundException e) {
        log.error("ProviderNotFoundException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.not_found",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProviderConflictException.class)
    public ResponseEntity<String> handleProviderConflictException(ProviderConflictException e) {
        log.error("ProviderConflictException: " + e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.conflict",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProviderMethodExecutionException.class)
    public ResponseEntity<String> handleProviderMethodExecutionException(ProviderMethodExecutionException e) {
        log.error("ProviderMethodExecutionException: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.execution_error",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnotherException(Exception e) {
        log.error("Exception: ", e);
        return new ResponseEntity<>(messageSource.getMessage("error.general.error_occurred",
                null,
                LocaleContextHolder.getLocale()) + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
