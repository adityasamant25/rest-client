package com.adityasamant.learnings.restclient.controller;

import com.adityasamant.learnings.restclient.CustomerServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestClientController.class)
//@RestClientTest(CustomerServiceClient.class)
class RestClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomerServiceClient client;

    String customers = null;

    @BeforeEach
    void setUp() {
        customers = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"country\":\"Australia\"}," +
                "{\"id\":2,\"firstName\":\"Alice\",\"lastName\":\"Smith\",\"country\":\"USA\"}," +
                "{\"id\":3,\"firstName\":\"Bob\",\"lastName\":\"Stevens\",\"country\":\"England\"}]";
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

}