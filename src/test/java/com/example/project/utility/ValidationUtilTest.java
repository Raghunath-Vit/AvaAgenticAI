package com.example.project.utility;

import com.example.project.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {
    @Test
    @DisplayName("validateName: valid name")
    void validateName_valid() {
        assertDoesNotThrow(() -> ValidationUtil.validateName("John Doe"));
    }

    @Test
    @DisplayName("validateName: empty name")
    void validateName_empty() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validateName(""));
    }

    @Test
    @DisplayName("validateName: too short")
    void validateName_tooShort() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validateName("J"));
    }

    @Test
    @DisplayName("validateName: too long")
    void validateName_tooLong() {
        String longName = "A".repeat(51);
        assertThrows(ValidationException.class, () -> ValidationUtil.validateName(longName));
    }

    @Test
    @DisplayName("validateName: invalid chars")
    void validateName_invalidChars() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validateName("John123"));
    }

    @Test
    @DisplayName("validateEmail: valid email")
    void validateEmail_valid() {
        assertDoesNotThrow(() -> ValidationUtil.validateEmail("john.doe@example.com"));
    }

    @Test
    @DisplayName("validateEmail: empty email")
    void validateEmail_empty() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validateEmail(""));
    }

    @Test
    @DisplayName("validateEmail: invalid format")
    void validateEmail_invalidFormat() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validateEmail("john.doe@.com"));
    }

    @Test
    @DisplayName("validatePhone: valid phone")
    void validatePhone_valid() {
        assertDoesNotThrow(() -> ValidationUtil.validatePhone("+12345678901"));
        assertDoesNotThrow(() -> ValidationUtil.validatePhone("1234567890"));
    }

    @Test
    @DisplayName("validatePhone: empty phone")
    void validatePhone_empty() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validatePhone(""));
    }

    @Test
    @DisplayName("validatePhone: invalid format")
    void validatePhone_invalidFormat() {
        assertThrows(ValidationException.class, () -> ValidationUtil.validatePhone("123-456-7890"));
    }
}
