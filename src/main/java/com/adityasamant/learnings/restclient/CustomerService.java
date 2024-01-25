package com.adityasamant.learnings.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerService {

    private final RestClient restClient;

    public CustomerService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8081").build();
    }

    public String findAllCustomers() {
        return restClient.get().uri("/api/customers").retrieve().body(String.class);
    }
}
