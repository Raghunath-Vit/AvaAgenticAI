package com.example.project.service;

import com.example.project.dto.CustomerRequestDTO;
import com.example.project.dto.CustomerResponseDTO;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO getCustomerById(String customerId);
    CustomerResponseDTO updateCustomer(String customerId, CustomerRequestDTO customerRequestDTO);
    void deleteCustomer(String customerId);
}
