package com.example.project.controller;

import com.example.project.dto.CustomerRequestDTO;
import com.example.project.dto.CustomerResponseDTO;
import com.example.project.exception.CustomerNotFoundException;
import com.example.project.exception.DuplicateEmailException;
import com.example.project.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerRequestDTO getValidRequest() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPhone("+12345678901");
        return dto;
    }

    private CustomerResponseDTO getValidResponse() {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId("abc123");
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPhone("+12345678901");
        return dto;
    }

    @Nested
    @DisplayName("POST /api/customers")
    class CreateCustomer {
        @Test
        @DisplayName("should create customer and return 201")
        void createCustomer_success() throws Exception {
            CustomerRequestDTO request = getValidRequest();
            CustomerResponseDTO response = getValidResponse();
            Mockito.when(customerService.createCustomer(any())).thenReturn(response);

            mockMvc.perform(post("/api/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value("abc123"))
                    .andExpect(jsonPath("$.name").value("John Doe"));
        }

        @Test
        @DisplayName("should return 400 for invalid input")
        void createCustomer_invalidInput() throws Exception {
            CustomerRequestDTO request = getValidRequest();
            request.setEmail("invalid-email");

            mockMvc.perform(post("/api/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 409 for duplicate email")
        void createCustomer_duplicateEmail() throws Exception {
            CustomerRequestDTO request = getValidRequest();
            Mockito.when(customerService.createCustomer(any()))
                    .thenThrow(new DuplicateEmailException("Email already exists"));

            mockMvc.perform(post("/api/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /api/customers/{id}")
    class GetCustomerById {
        @Test
        @DisplayName("should return customer for valid id")
        void getCustomerById_success() throws Exception {
            Mockito.when(customerService.getCustomerById("abc123")).thenReturn(getValidResponse());

            mockMvc.perform(get("/api/customers/abc123"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("abc123"));
        }

        @Test
        @DisplayName("should return 404 if customer not found")
        void getCustomerById_notFound() throws Exception {
            Mockito.when(customerService.getCustomerById("notfound"))
                    .thenThrow(new CustomerNotFoundException("Customer not found"));

            mockMvc.perform(get("/api/customers/notfound"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT /api/customers/{id}")
    class UpdateCustomer {
        @Test
        @DisplayName("should update and return customer")
        void updateCustomer_success() throws Exception {
            CustomerRequestDTO request = getValidRequest();
            CustomerResponseDTO response = getValidResponse();
            Mockito.when(customerService.updateCustomer(eq("abc123"), any())).thenReturn(response);

            mockMvc.perform(put("/api/customers/abc123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("abc123"));
        }

        @Test
        @DisplayName("should return 400 for invalid input")
        void updateCustomer_invalidInput() throws Exception {
            CustomerRequestDTO request = getValidRequest();
            request.setName("");

            mockMvc.perform(put("/api/customers/abc123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 404 if customer not found")
        void updateCustomer_notFound() throws Exception {
            CustomerRequestDTO request = getValidRequest();
            Mockito.when(customerService.updateCustomer(eq("notfound"), any()))
                    .thenThrow(new CustomerNotFoundException("Customer not found"));

            mockMvc.perform(put("/api/customers/notfound")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE /api/customers/{id}")
    class DeleteCustomer {
        @Test
        @DisplayName("should delete customer and return 204")
        void deleteCustomer_success() throws Exception {
            Mockito.doNothing().when(customerService).deleteCustomer("abc123");

            mockMvc.perform(delete("/api/customers/abc123"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("should return 404 if customer not found")
        void deleteCustomer_notFound() throws Exception {
            Mockito.doThrow(new CustomerNotFoundException("Customer not found"))
                    .when(customerService).deleteCustomer("notfound");

            mockMvc.perform(delete("/api/customers/notfound"))
                    .andExpect(status().isNotFound());
        }
    }
}
