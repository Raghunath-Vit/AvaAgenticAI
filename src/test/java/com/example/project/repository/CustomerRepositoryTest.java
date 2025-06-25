package com.example.project.repository;

import com.example.project.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("should save and find customer by id")
    void saveAndFindById() {
        Customer customer = new Customer(null, "John Doe", "john.doe@example.com", "+12345678901");
        Customer saved = customerRepository.save(customer);
        Optional<Customer> found = customerRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());
    }

    @Test
    @DisplayName("should find by email")
    void findByEmail() {
        Customer customer = new Customer(null, "Jane Doe", "jane.doe@example.com", "+12345678902");
        customerRepository.save(customer);
        Optional<Customer> found = customerRepository.findByEmail("jane.doe@example.com");
        assertTrue(found.isPresent());
        assertEquals("Jane Doe", found.get().getName());
    }

    @Test
    @DisplayName("should return empty for non-existing email")
    void findByEmail_notFound() {
        Optional<Customer> found = customerRepository.findByEmail("notfound@example.com");
        assertFalse(found.isPresent());
    }
}
