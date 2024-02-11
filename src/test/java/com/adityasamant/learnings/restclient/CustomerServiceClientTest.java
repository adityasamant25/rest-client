package com.adityasamant.learnings.restclient;

import com.adityasamant.learnings.customers.model.Customer;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceAuthorizationException;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceConnectionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

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
        String response = customerServiceClient.findAllCustomers(null);
        assertEquals(customers, response);

    }

    @Test
    void shouldFindAllCustomersWithHeader() {
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
        List<Customer> customerList = customerServiceClient.findAllCustomersAsList(null);
        assertEquals(3, customerList.size());
    }

    @Test
    void shouldFindAllCustomersAsListWithHeader() throws JsonProcessingException {
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

    @Test
    void shouldHandleResourceAccessExceptionInFindAllCustomers() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new ResourceAccessException("Connection reset", new SocketException()); });

        // then
        assertThrows(CustomerServiceConnectionException.class, () -> customerServiceClient.findAllCustomers(null));
    }

    @Test
    void shouldHandleResourceAccessExceptionInFindAllCustomersWithHeader() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new ResourceAccessException("Connection reset", new SocketException()); });

        // then
        assertThrows(CustomerServiceConnectionException.class, () -> customerServiceClient.findAllCustomers("debug"));
    }

    @Test
    void shouldHandleResourceAccessExceptionInFindAllCustomersWithList() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new ResourceAccessException("Connection reset", new SocketException()); });

        // then
        assertThrows(CustomerServiceConnectionException.class, () -> customerServiceClient.findAllCustomersAsList(null));
    }

    @Test
    void shouldHandleResourceAccessExceptionInFindAllCustomersWithHeaderWithList() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new ResourceAccessException("Connection reset", new SocketException()); });

        // then
        assertThrows(CustomerServiceConnectionException.class, () -> customerServiceClient.findAllCustomersAsList("debug"));
    }

    @Test
    void shouldHandleHttpClientErrorExceptionInFindAllCustomers() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "RBAC: access denied"); });

        // then
        assertThrows(CustomerServiceAuthorizationException.class, () -> customerServiceClient.findAllCustomers(null));
    }

    @Test
    void shouldHandleHttpClientErrorExceptionInFindAllCustomersWithHeader() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "RBAC: access denied"); });

        // then
        assertThrows(CustomerServiceAuthorizationException.class, () -> customerServiceClient.findAllCustomers("debug"));
    }

    @Test
    void shouldHandleHttpClientErrorExceptionInFindAllCustomersWithList() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "RBAC: access denied"); });

        // then
        assertThrows(CustomerServiceAuthorizationException.class, () -> customerServiceClient.findAllCustomersAsList(null));
    }

    @Test
    void shouldHandleHttpClientErrorExceptionInFindAllCustomersWithHeaderWithList() {
        // when
        server.expect(requestTo("http://localhost:8081/api/customers")).
                andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "RBAC: access denied"); });

        // then
        assertThrows(CustomerServiceAuthorizationException.class, () -> customerServiceClient.findAllCustomersAsList("debug"));
    }


}