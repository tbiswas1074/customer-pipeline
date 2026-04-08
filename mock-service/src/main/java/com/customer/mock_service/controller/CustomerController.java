package com.customer.mock_service.controller;

import com.customer.mock_service.entity.Customer;
import com.customer.mock_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/customers")
    public Map<String, Object> customers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        return service.getCustomers(page, limit);
    }

    @GetMapping("/customers/{id}")
    public Customer get(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
