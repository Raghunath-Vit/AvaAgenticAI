package com.example.project.controller;

import com.example.project.dto.CustomerRequest;
import com.example.project.dto.CustomerResponse;
import com.example.project.service.CustomerService;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.dto.ApiResponse;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse customer = customerService.createCustomer(request);
        ApiResponse<CustomerResponse> response = ApiResponse.success(customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        ApiResponse<CustomerResponse> response = ApiResponse.success(customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        ApiResponse<List<CustomerResponse>> response = ApiResponse.success(customers);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerRequest request) {
        CustomerResponse updated = customerService.updateCustomer(id, request);
        ApiResponse<CustomerResponse> response = ApiResponse.success(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        ApiResponse<Void> response = ApiResponse.success(null);
        return ResponseEntity.ok(response);
    }
}
