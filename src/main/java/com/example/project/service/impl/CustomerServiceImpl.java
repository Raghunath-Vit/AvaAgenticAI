package com.example.project.service.impl;

import com.example.project.dto.CustomerRequestDTO;
import com.example.project.dto.CustomerResponseDTO;
import com.example.project.entity.Customer;
import com.example.project.exception.CustomerNotFoundException;
import com.example.project.exception.DuplicateEmailException;
import com.example.project.exception.ValidationException;
import com.example.project.repository.CustomerRepository;
import com.example.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        validateCustomerRequest(customerRequestDTO);
        if (customerRepository.findByEmail(customerRequestDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists: " + customerRequestDTO.getEmail());
        }
        Customer customer = mapToEntity(customerRequestDTO);
        Customer saved = customerRepository.save(customer);
        return mapToResponseDTO(saved);
    }

    @Override
    public CustomerResponseDTO getCustomerById(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        return mapToResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(String customerId, CustomerRequestDTO customerRequestDTO) {
        validateCustomerRequest(customerRequestDTO);
        Customer existing = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        if (!existing.getEmail().equals(customerRequestDTO.getEmail()) &&
                customerRepository.findByEmail(customerRequestDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists: " + customerRequestDTO.getEmail());
        }
        existing.setName(customerRequestDTO.getName());
        existing.setEmail(customerRequestDTO.getEmail());
        existing.setPhone(customerRequestDTO.getPhone());
        Customer updated = customerRepository.save(existing);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }

    private void validateCustomerRequest(CustomerRequestDTO dto) {
        if (dto == null) {
            throw new ValidationException("Customer request must not be null");
        }
        if (!StringUtils.hasText(dto.getName())) {
            throw new ValidationException("Customer name must not be empty");
        }
        if (!StringUtils.hasText(dto.getEmail())) {
            throw new ValidationException("Customer email must not be empty");
        }
        // Add more validation as needed (e.g., email format, phone number)
    }

    private Customer mapToEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return customer;
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }
}
