package com.example.project.service.impl;

import com.example.project.dto.CustomerRequestDTO;
import com.example.project.dto.CustomerResponseDTO;
import com.example.project.entity.Customer;
import com.example.project.exception.CustomerNotFoundException;
import com.example.project.exception.DuplicateEmailException;
import com.example.project.exception.ValidationException;
import com.example.project.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CustomerRequestDTO getValidRequest() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPhone("+12345678901");
        return dto;
    }

    private Customer getValidCustomer() {
        return new Customer("abc123", "John Doe", "john.doe@example.com", "+12345678901");
    }

    @Nested
    @DisplayName("createCustomer")
    class CreateCustomer {
        @Test
        void success() {
            CustomerRequestDTO dto = getValidRequest();
            when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
            when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> {
                Customer c = inv.getArgument(0);
                c.setId("abc123");
                return c;
            });

            CustomerResponseDTO response = customerService.createCustomer(dto);
            assertEquals("John Doe", response.getName());
            assertEquals("abc123", response.getId());
        }

        @Test
        void duplicateEmail() {
            CustomerRequestDTO dto = getValidRequest();
            when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(getValidCustomer()));
            assertThrows(DuplicateEmailException.class, () -> customerService.createCustomer(dto));
        }

        @Test
        void nullRequest() {
            assertThrows(ValidationException.class, () -> customerService.createCustomer(null));
        }

        @Test
        void emptyName() {
            CustomerRequestDTO dto = getValidRequest();
            dto.setName("");
            assertThrows(ValidationException.class, () -> customerService.createCustomer(dto));
        }

        @Test
        void emptyEmail() {
            CustomerRequestDTO dto = getValidRequest();
            dto.setEmail("");
            assertThrows(ValidationException.class, () -> customerService.createCustomer(dto));
        }
    }

    @Nested
    @DisplayName("getCustomerById")
    class GetCustomerById {
        @Test
        void found() {
            when(customerRepository.findById("abc123")).thenReturn(Optional.of(getValidCustomer()));
            CustomerResponseDTO dto = customerService.getCustomerById("abc123");
            assertEquals("abc123", dto.getId());
        }

        @Test
        void notFound() {
            when(customerRepository.findById("notfound")).thenReturn(Optional.empty());
            assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById("notfound"));
        }
    }

    @Nested
    @DisplayName("updateCustomer")
    class UpdateCustomer {
        @Test
        void success() {
            CustomerRequestDTO dto = getValidRequest();
            Customer existing = getValidCustomer();
            when(customerRepository.findById("abc123")).thenReturn(Optional.of(existing));
            when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existing));
            when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

            CustomerResponseDTO response = customerService.updateCustomer("abc123", dto);
            assertEquals("John Doe", response.getName());
        }

        @Test
        void notFound() {
            CustomerRequestDTO dto = getValidRequest();
            when(customerRepository.findById("notfound")).thenReturn(Optional.empty());
            assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer("notfound", dto));
        }

        @Test
        void duplicateEmail() {
            CustomerRequestDTO dto = getValidRequest();
            Customer existing = getValidCustomer();
            Customer other = new Customer("otherId", "Jane", "other@example.com", "+12345678902");
            dto.setEmail("other@example.com");
            when(customerRepository.findById("abc123")).thenReturn(Optional.of(existing));
            when(customerRepository.findByEmail("other@example.com")).thenReturn(Optional.of(other));
            assertThrows(DuplicateEmailException.class, () -> customerService.updateCustomer("abc123", dto));
        }
    }

    @Nested
    @DisplayName("deleteCustomer")
    class DeleteCustomer {
        @Test
        void success() {
            when(customerRepository.existsById("abc123")).thenReturn(true);
            doNothing().when(customerRepository).deleteById("abc123");
            assertDoesNotThrow(() -> customerService.deleteCustomer("abc123"));
        }

        @Test
        void notFound() {
            when(customerRepository.existsById("notfound")).thenReturn(false);
            assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer("notfound"));
        }
    }
}
