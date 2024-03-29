package com.adityasamant.learnings.restclient.controller;

import com.adityasamant.learnings.customers.model.Customer;
import com.adityasamant.learnings.restclient.CustomerServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestClientController {

    private final CustomerServiceClient customerServiceClient;

    public RestClientController(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    @GetMapping("/customers")
    public String findAllCustomers(@RequestHeader(name = "user", required = false) String user) {
        return customerServiceClient.findAllCustomers(user);
    }

    @GetMapping("/customers/list")
    public List<Customer> findAllCustomersAsList(@RequestHeader(name = "user", required = false) String user) {
        return customerServiceClient.findAllCustomersAsList(user);
    }

}
