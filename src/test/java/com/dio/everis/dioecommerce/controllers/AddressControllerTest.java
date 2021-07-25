package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.builders.AddressDTOBuilder;
import com.dio.everis.dioecommerce.controllers.exceptions.ResourceExceptionHandler;
import com.dio.everis.dioecommerce.dto.AddressDTO;
import com.dio.everis.dioecommerce.services.AddressService;
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
public class AddressControllerTest {
    private static final String ADDRESS_API_URL_PATH = "/api/v1/addresses";
    private static final Long VALID_ADDRESS_ID = 1L;
    private static final Long INVALID_ADDRESS_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAAddressIsCreated() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();

        // when
        when(addressService.insert(addressDTO)).thenReturn(addressDTO);

        // then
        mockMvc.perform(post(ADDRESS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.publicPlace").value(addressDTO.getPublicPlace()))
                .andExpect(jsonPath("$.district").value(addressDTO.getDistrict()))
                .andExpect(jsonPath("$.number").value(addressDTO.getNumber()))
                .andExpect(jsonPath("$.zipCode").value(addressDTO.getZipCode()));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        addressDTO.setPublicPlace(null);

        // then
        mockMvc.perform(post(ADDRESS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();

        // when
        when(addressService.find(VALID_ADDRESS_ID)).thenReturn(addressDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ADDRESS_API_URL_PATH + "/" + VALID_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicPlace").value(addressDTO.getPublicPlace()))
                .andExpect(jsonPath("$.district").value(addressDTO.getDistrict()))
                .andExpect(jsonPath("$.number").value(addressDTO.getNumber()))
                .andExpect(jsonPath("$.zipCode").value(addressDTO.getZipCode()));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(addressService.find(INVALID_ADDRESS_ID)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ADDRESS_API_URL_PATH + "/" + INVALID_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithAddressIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();

        // when
        when(addressService.findAll()).thenReturn(Collections.singletonList(addressDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ADDRESS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].publicPlace").value(addressDTO.getPublicPlace()))
                .andExpect(jsonPath("$[0].district").value(addressDTO.getDistrict()))
                .andExpect(jsonPath("$[0].number").value(addressDTO.getNumber()))
                .andExpect(jsonPath("$[0].zipCode").value(addressDTO.getZipCode()));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
        doNothing().when(addressService).delete(VALID_ADDRESS_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ADDRESS_API_URL_PATH + "/" + VALID_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ObjectNotFoundException.class).when(addressService).delete(INVALID_ADDRESS_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ADDRESS_API_URL_PATH + "/" + INVALID_ADDRESS_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenAAddressIsOkStatusReturned() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        addressDTO.setPublicPlace("Modified Public Place");
        // when
        when(addressService.update(addressDTO)).thenReturn(addressDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(ADDRESS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicPlace", is("Modified Public Place")));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();

        // when
        when(addressService.update(addressDTO)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(ADDRESS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        AddressDTO addressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        addressDTO.setPublicPlace(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(ADDRESS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isBadRequest());
    }

}
