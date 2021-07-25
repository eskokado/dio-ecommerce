package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.builders.StateDTOBuilder;
import com.dio.everis.dioecommerce.controllers.exceptions.ResourceExceptionHandler;
import com.dio.everis.dioecommerce.dto.StateDTO;
import com.dio.everis.dioecommerce.services.StateService;
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
public class StateControllerTest {
    private static final String STATE_API_URL_PATH = "/api/v1/states";
    private static final Integer VALID_STATE_ID = 1;
    private static final Integer INVALID_STATE_ID = 2;

    private MockMvc mockMvc;

    @Mock
    private StateService stateService;

    @InjectMocks
    private StateController stateController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAStateIsCreated() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();

        // when
        when(stateService.insert(stateDTO)).thenReturn(stateDTO);

        // then
        mockMvc.perform(post(STATE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(stateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(stateDTO.getName()))
                .andExpect(jsonPath("$.initials").value(stateDTO.getInitials()));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();
        stateDTO.setName(null);

        // then
        mockMvc.perform(post(STATE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(stateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();

        // when
        when(stateService.find(VALID_STATE_ID)).thenReturn(stateDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STATE_API_URL_PATH + "/" + VALID_STATE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(stateDTO.getName()))
                .andExpect(jsonPath("$.initials").value(stateDTO.getInitials()));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(stateService.find(INVALID_STATE_ID)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STATE_API_URL_PATH + "/" + INVALID_STATE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithStateIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();

        // when
        when(stateService.findAll()).thenReturn(Collections.singletonList(stateDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STATE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(stateDTO.getName()))
                .andExpect(jsonPath("$[0].initials").value(stateDTO.getInitials()));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
        doNothing().when(stateService).delete(VALID_STATE_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(STATE_API_URL_PATH + "/" + VALID_STATE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ObjectNotFoundException.class).when(stateService).delete(INVALID_STATE_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(STATE_API_URL_PATH + "/" + INVALID_STATE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenAStateIsOkStatusReturned() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();
        stateDTO.setName("Modified Name");
        stateDTO.setInitials("MN");
        // when
        when(stateService.update(stateDTO)).thenReturn(stateDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(STATE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(stateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Modified Name")))
                .andExpect(jsonPath("$.initials", is("MN")));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();

        // when
        when(stateService.update(stateDTO)).thenThrow(ObjectNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(STATE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(stateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        StateDTO stateDTO = StateDTOBuilder.builder().build().toStateDTO();
        stateDTO.setName(null);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(STATE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(stateDTO)))
                .andExpect(status().isBadRequest());
    }

}
