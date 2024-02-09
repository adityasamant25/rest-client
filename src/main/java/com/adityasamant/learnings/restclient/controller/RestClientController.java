package com.adityasamant.learnings.restclient.controller;

import com.adityasamant.learnings.restclient.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestClientController {

    private final CustomerService customerService;

    public RestClientController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String findAllCustomers(@RequestHeader(name = "user", required = false) String user) {
        return customerService.findAllCustomers(user);
    }

}
