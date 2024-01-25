package com.adityasamant.learnings.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerService {

    private final RestClient restClient;

    public CustomerService(RestClient.Builder builder, @Value("${customer.service.url}") String customerServiceUrl) {
        this.restClient = builder.baseUrl(customerServiceUrl).build();
    }

    public String findAllCustomers() {
        return restClient.get().uri("/api/customers").retrieve().body(String.class);
    }
}
