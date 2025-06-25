package com.example.project.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorHandlerTest {
    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    @DisplayName("handleCustomerNotFoundException returns NOT_FOUND")
    void handleCustomerNotFoundException() {
        CustomerNotFoundException ex = new CustomerNotFoundException("not found");
        ResponseEntity<Map<String, Object>> response = errorHandler.handleCustomerNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer Not Found", response.getBody().get("error"));
    }

    @Test
    @DisplayName("handleDuplicateEmailException returns CONFLICT")
    void handleDuplicateEmailException() {
        DuplicateEmailException ex = new DuplicateEmailException("duplicate");
        ResponseEntity<Map<String, Object>> response = errorHandler.handleDuplicateEmailException(ex);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Duplicate Email", response.getBody().get("error"));
    }

    @Test
    @DisplayName("handleValidationException returns BAD_REQUEST")
    void handleValidationException() {
        ValidationException ex = new ValidationException("validation");
        ResponseEntity<Map<String, Object>> response = errorHandler.handleValidationException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation Error", response.getBody().get("error"));
    }

    @Test
    @DisplayName("handleUnauthorizedException returns UNAUTHORIZED")
    void handleUnauthorizedException() {
        UnauthorizedException ex = new UnauthorizedException("unauthorized");
        ResponseEntity<Map<String, Object>> response = errorHandler.handleUnauthorizedException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized", response.getBody().get("error"));
    }

    @Test
    @DisplayName("handleMethodArgumentNotValidException returns BAD_REQUEST with field errors")
    void handleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("object", "name", "must not be blank"),
                new FieldError("object", "email", "must be valid")
        ));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Map<String, Object>> response = errorHandler.handleMethodArgumentNotValidException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation Error", response.getBody().get("error"));
        assertTrue(response.getBody().get("message").toString().contains("name: must not be blank"));
    }

    @Test
    @DisplayName("handleGenericException returns INTERNAL_SERVER_ERROR")
    void handleGenericException() {
        Exception ex = new Exception("unexpected");
        ResponseEntity<Map<String, Object>> response = errorHandler.handleGenericException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().get("error"));
    }
}
