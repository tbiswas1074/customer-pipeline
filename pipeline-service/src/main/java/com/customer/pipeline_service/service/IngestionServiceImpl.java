package com.customer.pipeline_service.service;

import com.customer.pipeline_service.entity.Customer;
import com.customer.pipeline_service.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IngestionServiceImpl implements IngestionService {

    private final RestTemplate restTemplate;
    private final CustomerRepository repository;
    private final ObjectMapper objectMapper;

    @Value("${customer.api.url}")
    private String customerApiUrl;

    @Transactional
    @Override
    public int ingest() {

        int page = 0;
        int processed = 0;

        while (true) {

            String api = String.format(customerApiUrl, page);

            Map<String, Object> response =
                    restTemplate.getForObject(api, Map.class);

            if (response == null || !response.containsKey("data"))
                break;

            List<Map<String, Object>> data =
                    (List<Map<String, Object>>) response.get("data");

            if (data == null || data.isEmpty())
                break;

            List<Customer> customers = data.stream()
                    .map(c -> objectMapper.convertValue(c, Customer.class))
                    .toList();
            repository.saveAll(customers);

            processed += customers.size();
            page++;
        }

        return processed;
    }
}