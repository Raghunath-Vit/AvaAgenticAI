package com.example.project.utility;

import com.example.project.exception.ValidationException;
import java.util.regex.Pattern;

/**
 * Utility class for validating customer fields.
 * Provides static methods for name, email, and phone validation.
 */
public final class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{10,15}$"); // Allows optional +, 10-15 digits

    private ValidationUtil() {
        // Prevent instantiation
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Customer name must not be empty.");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new ValidationException("Customer name must be between 2 and 50 characters.");
        }
        if (!name.matches("^[A-Za-z .'-]+$")) {
            throw new ValidationException("Customer name contains invalid characters.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Customer email must not be empty.");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Customer email format is invalid.");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Customer phone number must not be empty.");
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException("Customer phone number format is invalid. It should contain only digits and may start with '+'.");
        }
    }
}
