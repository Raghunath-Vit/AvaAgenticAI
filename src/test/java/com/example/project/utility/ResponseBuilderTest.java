package com.example.project.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResponseBuilderTest {

    @Test
    @DisplayName("should build success response with data and message")
    void buildSuccessResponse() {
        Object data = "testData";
        String message = "Success!";
        Map<String, Object> response = ResponseBuilder.buildSuccessResponse(data, message);
        assertEquals("success", response.get("status"));
        assertEquals(data, response.get("data"));
        assertNull(response.get("error"));
        assertEquals(message, response.get("message"));
        assertNotNull(response.get("timestamp"));
    }

    @Test
    @DisplayName("should build error response with error and message")
    void buildErrorResponse() {
        Object error = "someError";
        String message = "Error occurred";
        Map<String, Object> response = ResponseBuilder.buildErrorResponse(error, message);
        assertEquals("error", response.get("status"));
        assertNull(response.get("data"));
        assertEquals(error, response.get("error"));
        assertEquals(message, response.get("message"));
        assertNotNull(response.get("timestamp"));
    }
}
