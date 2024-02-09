package com.adityasamant.learnings.restclient;

import com.adityasamant.learnings.customers.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(CustomerServiceClient.class)
class CustomerServiceClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    CustomerServiceClient customerServiceClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllCustomers() throws JsonProcessingException {
        // given
        String customers = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"country\":\"Australia\"}," +
                "{\"id\":2,\"firstName\":\"Alice\",\"lastName\":\"Smith\",\"country\":\"USA\"}," +
                "{\"id\":3,\"firstName\":\"Bob\",\"lastName\":\"Stevens\",\"country\":\"England\"}]";

        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond(withSuccess(customers, null));

        // then
        String response = customerServiceClient.findAllCustomers("debug");
        assertEquals(customers, response);

    }
    @Test
    void shouldFindAllCustomersAsList() throws JsonProcessingException {
        // given
        List<Customer> customers = List.of(new Customer(1, "John", "Doe", "Australia"),
                new Customer(2, "Alice", "Smith", "USA"),
                new Customer(3, "Bob", "Stevens", "England"));

        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond(withSuccess(objectMapper.writeValueAsString(customers), MediaType.APPLICATION_JSON));

        // then
        List<Customer> customerList = customerServiceClient.findAllCustomersAsList("debug");
        assertEquals(3, customerList.size());
    }
}