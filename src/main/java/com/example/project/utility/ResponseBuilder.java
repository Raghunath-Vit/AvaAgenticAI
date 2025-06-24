package com.example.project.utility;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for building standardized API responses.
 */
public final class ResponseBuilder {

    private ResponseBuilder() {
        // Prevent instantiation
    }

    public static Map<String, Object> buildSuccessResponse(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);
        response.put("error", null);
        response.put("message", message);
        response.put("timestamp", Instant.now().toString());
        return response;
    }

    public static Map<String, Object> buildErrorResponse(Object error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("data", null);
        response.put("error", error);
        response.put("message", message);
        response.put("timestamp", Instant.now().toString());
        return response;
    }
}
