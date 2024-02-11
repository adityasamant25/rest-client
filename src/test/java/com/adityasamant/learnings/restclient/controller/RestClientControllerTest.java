package com.adityasamant.learnings.restclient.controller;

import com.adityasamant.learnings.customers.model.Customer;
import com.adityasamant.learnings.restclient.CustomerServiceClient;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceAuthorizationException;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceConnectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestClientController.class)
class RestClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomerServiceClient client;

    String customers = null;
    List<Customer> customersAsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        customers = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"country\":\"Australia\"}," +
                "{\"id\":2,\"firstName\":\"Alice\",\"lastName\":\"Smith\",\"country\":\"USA\"}," +
                "{\"id\":3,\"firstName\":\"Bob\",\"lastName\":\"Stevens\",\"country\":\"England\"}]";

        customersAsList = List.of(new Customer(1, "John", "Doe", "Australia"),
                new Customer(2, "Alice", "Smith", "USA"),
                new Customer(3, "Bob", "Stevens", "England"));
    }

    @Test
    void shouldFindAllCustomers() throws Exception {
        // when
        when(client.findAllCustomers(null)).thenReturn(customers);

        // then
        mvc.perform(get("/api/customers")).andExpect(status().isOk()).andExpect(content().string(customers));
    }

    @Test
    void shouldFindAllCustomersWithHeader() throws Exception {
        // when
        when(client.findAllCustomers("debug")).thenReturn(customers);

        // then
        mvc.perform(get("/api/customers").header("user", "debug")).andExpect(status().isOk()).andExpect(content().string(customers));
    }

    @Test
    void shouldFindAllCustomersAsList() throws Exception {
        // when
        when(client.findAllCustomersAsList(null)).thenReturn(customersAsList);

        // then
        mvc.perform(get("/api/customers/list")).andExpect(status().isOk()).andExpect(content().json(customers));
    }

    @Test
    void shouldFindAllCustomersAsListWithHeader() throws Exception {
        // when
        when(client.findAllCustomersAsList("debug")).thenReturn(customersAsList);

        // then
        mvc.perform(get("/api/customers/list").header("user", "debug")).andExpect(status().isOk()).andExpect(content().json(customers));
    }

    @Test
    void shouldThrowExceptionWhenCustomerServiceDeniesConnection() throws Exception {
        // when
        when(client.findAllCustomers(null)).thenThrow(CustomerServiceConnectionException.class);

        // then
        mvc.perform(get("/api/customers")).andExpect(status().isBadGateway());
    }

    @Test
    void shouldThrowExceptionWhenCustomerServiceDeniesConnectionWithHeader() throws Exception {
        // when
        when(client.findAllCustomers("debug")).thenThrow(CustomerServiceConnectionException.class);

        // then
        mvc.perform(get("/api/customers").header("user", "debug")).andExpect(status().isBadGateway());
    }

    @Test
    void shouldThrowExceptionWhenCustomerServiceDeniesConnectionWithList() throws Exception {
        // when
        when(client.findAllCustomersAsList(null)).thenThrow(CustomerServiceConnectionException.class);

        // then
        mvc.perform(get("/api/customers/list")).andExpect(status().isBadGateway());
    }

    @Test
    void shouldThrowExceptionWhenCustomerServiceDeniesConnectionWithHeaderWithList() throws Exception {
        // when
        when(client.findAllCustomersAsList("debug")).thenThrow(CustomerServiceConnectionException.class);

        // then
        mvc.perform(get("/api/customers/list").header("user", "debug")).andExpect(status().isBadGateway());
    }

    @Test
    void shouldThrowRBACExceptionWhenCustomerServiceDeniesAuthorization() throws Exception {
        // when
        when(client.findAllCustomers(null)).thenThrow(CustomerServiceAuthorizationException.class);

        // then
        mvc.perform(get("/api/customers")).andExpect(status().isForbidden());
    }

    @Test
    void shouldThrowRBACExceptionWhenCustomerServiceDeniesAuthorizationWithHeader() throws Exception {
        // when
        when(client.findAllCustomers("debug")).thenThrow(CustomerServiceAuthorizationException.class);

        // then
        mvc.perform(get("/api/customers").header("user", "debug")).andExpect(status().isForbidden());
    }

    @Test
    void shouldThrowRBACExceptionWhenCustomerServiceDeniesAuthorizationWithList() throws Exception {
        // when
        when(client.findAllCustomersAsList(null)).thenThrow(CustomerServiceAuthorizationException.class);

        // then
        mvc.perform(get("/api/customers/list")).andExpect(status().isForbidden());
    }

    @Test
    void shouldThrowRBACExceptionWhenCustomerServiceDeniesAuthorizationWithHeaderWithList() throws Exception {
        // when
        when(client.findAllCustomersAsList("debug")).thenThrow(CustomerServiceAuthorizationException.class);

        // then
        mvc.perform(get("/api/customers/list").header("user", "debug")).andExpect(status().isForbidden());
    }
}