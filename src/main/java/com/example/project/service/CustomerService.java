package com.example.project.service;

import com.example.project.entity.Customer;
import com.example.project.repository.CustomerRepository;
import com.example.project.exception.CustomerNotFoundException;
import com.example.project.exception.DuplicateEmailException;
import com.example.project.exception.ValidationException;
import com.example.project.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
    }

    public Customer createCustomer(Customer customer) {
        // Validate input
        validationUtil.validateCustomer(customer);

        // Check for duplicate email
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + customer.getEmail());
        }

        try {
            return customerRepository.save(customer);
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException("Email already exists: " + customer.getEmail());
        }
    }

    public Customer getCustomerById(String customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        return customerOpt.orElseThrow(() ->
            new CustomerNotFoundException("Customer not found with id: " + customerId)
        );
    }

    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        // Validate input
        validationUtil.validateCustomer(updatedCustomer);

        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        // Check for email change and duplicate email
        if (!existingCustomer.getEmail().equals(updatedCustomer.getEmail()) &&
                customerRepository.existsByEmail(updatedCustomer.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + updatedCustomer.getEmail());
        }

        // Update fields
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        // Add other fields as necessary

        try {
            return customerRepository.save(existingCustomer);
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException("Email already exists: " + updatedCustomer.getEmail());
        }
    }

    public void deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }
}
