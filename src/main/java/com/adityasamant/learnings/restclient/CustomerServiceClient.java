package com.adityasamant.learnings.restclient;

import com.adityasamant.learnings.customers.model.Customer;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceAuthorizationException;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CustomerServiceClient {

    private final RestClient restClient;

    public CustomerServiceClient(RestClient.Builder builder, @Value("${customer.service.url}") String customerServiceUrl) {
        this.restClient = builder.baseUrl(customerServiceUrl).build();
    }

    public String findAllCustomers(String user) {
        try {
            return restClient.get().uri("/api/customers").header("user", user).retrieve().body(String.class);
        } catch (ResourceAccessException resourceAccessException) {
            throw new CustomerServiceConnectionException(resourceAccessException.getMessage());
        }
        catch (HttpClientErrorException httpClientErrorException) {
            throw new CustomerServiceAuthorizationException(httpClientErrorException.getMessage());
        }
    }

    public List<Customer> findAllCustomersAsList(String user) {
        try {
            return restClient.get().uri("/api/customers").header("user", user).retrieve().body(new ParameterizedTypeReference<>() {
            });
        }
        catch (ResourceAccessException resourceAccessException) {
            throw new CustomerServiceConnectionException(resourceAccessException.getMessage());
        }
        catch (HttpClientErrorException httpClientErrorException) {
            throw new CustomerServiceAuthorizationException(httpClientErrorException.getMessage());
        }

    }
}
