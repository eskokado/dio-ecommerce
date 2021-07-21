package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.StateDTOBuilder;
import com.dio.everis.dioecommerce.dto.StateDTO;
import com.dio.everis.dioecommerce.entities.State;
import com.dio.everis.dioecommerce.mappers.StateMapper;
import com.dio.everis.dioecommerce.repositories.StateRepository;
import com.dio.everis.dioecommerce.services.exceptions.ObjectAlreadyRegisteredException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Data
@ExtendWith(MockitoExtension.class)
public class StateServiceTest {
    private static final Integer VALID_STATE_ID = 1;
    private static final Integer INVALID_STATE_ID = 2;

    @Mock
    private StateRepository stateRepository;

    private StateMapper stateMapper = StateMapper.INSTANCE;

    @InjectMocks
    private StateService stateService;

    @Test
    void whenStateInformedThenItShouldBeCreated() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();
        State expectedState = stateMapper.toModel(expectedStateDTO);

        // when
        when(stateRepository.findById(VALID_STATE_ID)).thenReturn(Optional.empty());
        when(stateRepository.save(expectedState)).thenReturn(expectedState);

        // then
        StateDTO createdStateDTO = stateService.insert(expectedStateDTO);

        assertThat(createdStateDTO, is(equalTo(expectedStateDTO)));
    }

    @Test
    void whenAlreadyRegisteredStateInformedThenAnExceptionShouldBeThrown() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();
        State expectedState = stateMapper.toModel(expectedStateDTO);

        // when
        when(stateRepository.findById(VALID_STATE_ID)).thenReturn(Optional.of(expectedState));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> stateService.insert(expectedStateDTO));
    }

    @Test
    void whenValidStateIdIsGivenThenReturnAnState() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();
        State expectedState = stateMapper.toModel(expectedStateDTO);

        // when
        when(stateRepository.findById(VALID_STATE_ID)).thenReturn(Optional.of(expectedState));

        // then
        StateDTO foundStateDTO = stateService.find(VALID_STATE_ID);

        assertThat(foundStateDTO, is(equalTo(expectedStateDTO)));
    }

    @Test
    void whenNotRegisteredStateIdGivenThenThrowAnException() {
        // when
        when(stateRepository.findById(INVALID_STATE_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> stateService.find(INVALID_STATE_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidStateGivenThenReturnAStateUpdated() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();
        State expectedState = stateMapper.toModel(expectedStateDTO);

        // when
        when(stateRepository.findById(VALID_STATE_ID)).thenReturn(Optional.of(expectedState));
        when(stateRepository.save(expectedState)).thenReturn(expectedState);

        // then
        StateDTO updateStateDTO = stateService.update(expectedStateDTO);

        assertThat(updateStateDTO, is(equalTo(expectedStateDTO)));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingStateThenAnExceptionShouldBeThrown() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();

        // when
        when(stateRepository.findById(VALID_STATE_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> stateService.update(expectedStateDTO));
    }

    @Test
    void whenListStateIsCalledThenReturnAListOfState() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();
        State expectedState = stateMapper.toModel(expectedStateDTO);

        // when
        when(stateRepository.findAll()).thenReturn(Collections.singletonList(expectedState));

        // then
        List<StateDTO> listStateDTO = stateService.findAll();

        assertThat(listStateDTO, is(not(empty())));
        assertThat(listStateDTO.get(0), is(equalTo(expectedStateDTO)));
    }

    @Test
    void whenListStateIsCalledThenReturnAnEmptyListOfState() {
        // when
        when(stateRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<StateDTO> listStateDTO = stateService.findAll();

        assertThat(listStateDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAStateShouldBeDeleted() {
        // given
        StateDTO expectedStateDTO = StateDTOBuilder.builder().build().toStateDTO();
        State expectedState = stateMapper.toModel(expectedStateDTO);

        //  when
        when(stateRepository.findById(VALID_STATE_ID)).thenReturn(Optional.of(expectedState));
        doNothing().when(stateRepository).deleteById(VALID_STATE_ID);

        // then
        stateService.delete(VALID_STATE_ID);

        verify(stateRepository, times(1)).findById(VALID_STATE_ID);
        verify(stateRepository, times(1)).deleteById(VALID_STATE_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingStateThenAnExceptionShouldBeThrown() {
        // when
        when(stateRepository.findById(INVALID_STATE_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> stateService.delete(INVALID_STATE_ID));
    }
}
