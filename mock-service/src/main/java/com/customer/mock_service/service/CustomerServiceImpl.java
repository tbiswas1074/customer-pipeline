package com.customer.mock_service.service;

import com.customer.mock_service.entity.Customer;
import com.customer.mock_service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService{
    private List<Customer> customers;

    @PostConstruct
    public void loadData() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        InputStream is =
                new ClassPathResource("data/customers.json")
                        .getInputStream();

        customers = Arrays.asList(
                mapper.readValue(is, Customer[].class)
        );
    }

    public Map<String, Object> getCustomers(int page, int limit) {

        int total = customers.size();

        int start = page * limit;

        // prevent index error
        if (start >= total) {
            return Map.of(
                    "data", List.of(),
                    "total", total,
                    "page", page,
                    "limit", limit
            );
        }

        int end = Math.min(start + limit, total);

        List<Customer> subList = customers.subList(start, end);

        return Map.of(
                "data", subList,
                "total", total,
                "page", page,
                "limit", limit
        );
    }
    public Customer getById(String id) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id
                        )
                );
    }
}
