package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.builders.CustomerDTOBuilder;
import com.dio.everis.dioecommerce.controllers.exceptions.ResourceExceptionHandler;
import com.dio.everis.dioecommerce.dto.CustomerDTO;
import com.dio.everis.dioecommerce.services.CustomerService;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.dio.everis.dioecommerce.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    private static final String CUSTOMER_API_URL_PATH = "/api/v1/customers";
    private static final Long VALID_CUSTOMER_ID = 1L;
    private static final Long INVALID_CUSTOMER_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenACustomerIsCreated() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();

        // when
        when(customerService.insert(customerDTO)).thenReturn(customerDTO);

        // then
        mockMvc.perform(post(CUSTOMER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(customerDTO.getName()));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        customerDTO.setName(null);

        // then
        mockMvc.perform(post(CUSTOMER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();

        // when
        when(customerService.find(VALID_CUSTOMER_ID)).thenReturn(customerDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_API_URL_PATH + "/" + VALID_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customerDTO.getName()));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(customerService.find(INVALID_CUSTOMER_ID)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_API_URL_PATH + "/" + INVALID_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithCustomerIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();

        // when
        when(customerService.findAll()).thenReturn(Collections.singletonList(customerDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(customerDTO.getName()));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
        doNothing().when(customerService).delete(VALID_CUSTOMER_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CUSTOMER_API_URL_PATH + "/" + VALID_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ObjectNotFoundException.class).when(customerService).delete(INVALID_CUSTOMER_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CUSTOMER_API_URL_PATH + "/" + INVALID_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenACustomerIsOkStatusReturned() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        customerDTO.setName("Modified Name");
        // when
        when(customerService.update(customerDTO)).thenReturn(customerDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CUSTOMER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Modified Name")));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();

        // when
        when(customerService.update(customerDTO)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CUSTOMER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        customerDTO.setName(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CUSTOMER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isBadRequest());
    }

}
