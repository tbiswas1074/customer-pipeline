package com.customer.mock_service.service;

import com.customer.mock_service.entity.Customer;

import java.util.Map;

public interface CustomerService {
    

    Customer getById(String id);

    Map<String, Object> getCustomers(int page, int limit);
}
