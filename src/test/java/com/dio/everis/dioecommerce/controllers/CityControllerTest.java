package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.builders.CityDTOBuilder;
import com.dio.everis.dioecommerce.controllers.exceptions.ResourceExceptionHandler;
import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.services.CityService;
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
public class CityControllerTest {
    private static final String CITY_API_URL_PATH = "/api/v1/cities";
    private static final Integer VALID_CITY_ID = 1;
    private static final Integer INVALID_CITY_ID = 2;

    private MockMvc mockMvc;

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cityController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenACityIsCreated() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();

        // when
        when(cityService.insert(cityDTO)).thenReturn(cityDTO);

        // then
        mockMvc.perform(post(CITY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(cityDTO.getName()))
                .andExpect(jsonPath("$.initials").value(cityDTO.getInitials()))
                .andExpect(jsonPath("$.state.name").value(cityDTO.getState().getName()))
                .andExpect(jsonPath("$.state.initials").value(cityDTO.getState().getInitials()));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();
        cityDTO.setName(null);

        // then
        mockMvc.perform(post(CITY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();

        // when
        when(cityService.find(VALID_CITY_ID)).thenReturn(cityDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CITY_API_URL_PATH + "/" + VALID_CITY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(cityDTO.getName()))
                .andExpect(jsonPath("$.initials").value(cityDTO.getInitials()))
                .andExpect(jsonPath("$.state.name").value(cityDTO.getState().getName()))
                .andExpect(jsonPath("$.state.initials").value(cityDTO.getState().getInitials()));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(cityService.find(INVALID_CITY_ID)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CITY_API_URL_PATH + "/" + INVALID_CITY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithCityIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();

        // when
        when(cityService.findAll()).thenReturn(Collections.singletonList(cityDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CITY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(cityDTO.getName()))
                .andExpect(jsonPath("$[0].initials").value(cityDTO.getInitials()))
                .andExpect(jsonPath("$[0].state.name").value(cityDTO.getState().getName()))
                .andExpect(jsonPath("$[0].state.initials").value(cityDTO.getState().getInitials()));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
        doNothing().when(cityService).delete(VALID_CITY_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CITY_API_URL_PATH + "/" + VALID_CITY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ObjectNotFoundException.class).when(cityService).delete(INVALID_CITY_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CITY_API_URL_PATH + "/" + INVALID_CITY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenACityIsOkStatusReturned() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();
        cityDTO.setName("Modified Name");
        cityDTO.setInitials("MN");
        // when
        when(cityService.update(cityDTO)).thenReturn(cityDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CITY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Modified Name")))
                .andExpect(jsonPath("$.initials", is("MN")))
                .andExpect(jsonPath("$.state.name").value(cityDTO.getState().getName()))
                .andExpect(jsonPath("$.state.initials").value(cityDTO.getState().getInitials()));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();

        // when
        when(cityService.update(cityDTO)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CITY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        CityDTO cityDTO = CityDTOBuilder.builder().build().toCityDTO();
        cityDTO.setName(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CITY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cityDTO)))
                .andExpect(status().isBadRequest());
    }

}
