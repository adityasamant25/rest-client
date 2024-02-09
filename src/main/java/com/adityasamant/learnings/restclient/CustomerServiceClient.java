package com.adityasamant.learnings.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CustomerServiceClient {

    private final RestClient restClient;

    public CustomerServiceClient(RestClient.Builder builder, @Value("${customer.service.url}") String customerServiceUrl) {
        this.restClient = builder.baseUrl(customerServiceUrl).build();
    }

    public String findAllCustomers(String user) {
        return restClient.get().uri("/api/customers").header("user", user).retrieve().body(String.class);
    }

}
