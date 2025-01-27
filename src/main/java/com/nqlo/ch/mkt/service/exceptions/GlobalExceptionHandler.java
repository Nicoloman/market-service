package com.nqlo.ch.mkt.service.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nqlo.ch.mkt.service.entities.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle duplicate entry
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntryException(DuplicateEntryException ex) {
        String message = "Duplicated entry for field '" + ex.getField() + "'.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message, "Bad Request", HttpStatus.BAD_REQUEST.value()));
    }

    // Handle generic DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "A unique constraint was violated.";
        
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            String causeMessage = ex.getCause().getMessage();
            
            if (causeMessage.contains("Duplicate entry")) {
                String field = extractFieldFromConstraint(causeMessage);
                message = field != null 
                    ? "Duplicated entry for field '" + field + "'." 
                    : "A unique constraint violation occurred.";
            }
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message, "Bad Request", HttpStatus.BAD_REQUEST.value()));
    }

    private String extractFieldFromConstraint(String causeMessage) {
        int startIndex = causeMessage.indexOf("for key");
        if (startIndex != -1) {
            String[] parts = causeMessage.split("for key");
            if (parts.length > 1) {
                String keyInfo = parts[1].trim();
                if (keyInfo.contains(".")) {
                    return keyInfo.substring(keyInfo.lastIndexOf('.') + 1).replace("'", "");
                }
            }
        }
        return null;
    }

    // Handle 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), "Not Found", HttpStatus.NOT_FOUND.value()));
    }

    // General exception handler for other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An unexpected error occurred: " + ex.getMessage(), "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}