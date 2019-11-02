package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.service.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private CustomerDto validCustomer;

    private final static String CUSTOMER_API_URL = "/api/v1/customer/";

    @BeforeEach
    void setUp() {
        validCustomer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Joi")
                .build();
    }

    @Test
    void testGetCustomer() throws Exception {
        given(customerService.getCustomer(any(UUID.class))).willReturn(validCustomer);

        mockMvc.perform(
                get(CUSTOMER_API_URL + validCustomer.getId().toString())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(validCustomer.getName())));
    }

    @Test
    void testHandlePost() throws Exception {
        CustomerDto customer = validCustomer;
        customer.setId(null);
        CustomerDto savedCustomer = CustomerDto.builder().id(UUID.randomUUID()).name("Why").build();
        String customerJson = objectMapper.writeValueAsString(customer);

        given(customerService.save(any())).willReturn(savedCustomer);

        mockMvc.perform(
                post(CUSTOMER_API_URL)
                        .contentType(APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testHandleUpdate() throws Exception {
        CustomerDto customer = validCustomer;
        customer.setId(null);
        String customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(
                put(CUSTOMER_API_URL + UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isNoContent());

        then(customerService).should().update(any(), any());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(
                delete(CUSTOMER_API_URL + UUID.randomUUID())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        then(customerService).should().delete(any());
    }

}
