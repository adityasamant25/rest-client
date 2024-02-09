package com.adityasamant.learnings.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldFindAllCustomers() {
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

}