package com.customer.pipeline_service.controller;

import com.customer.pipeline_service.entity.Customer;
import com.customer.pipeline_service.exception.ResourceNotFoundException;
import com.customer.pipeline_service.repository.CustomerRepository;
import com.customer.pipeline_service.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService service;
    private final CustomerRepository repo;

    @GetMapping("/ingest")
    public ResponseEntity<Map<String,Object>> ingest() {

        int count = service.ingest();
        Map<String, Object> response = Map.of(
                "status", "success",
                "records_processed", count
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers")
    public Page<Customer> customers(
            @RequestParam int page,
            @RequestParam int limit) {

        return repo.findAll(PageRequest.of(page, limit));
    }

    @GetMapping("/customers/{id}")
    public Customer get(@PathVariable String id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id
                        )
                );
    }
}
