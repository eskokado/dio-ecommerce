package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.CityDTOBuilder;
import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.entities.City;
import com.dio.everis.dioecommerce.mappers.CityMapper;
import com.dio.everis.dioecommerce.repositories.CityRepository;
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
public class CityServiceTest {
    private static final Integer VALID_CITY_ID = 1;
    private static final Integer INVALID_CITY_ID = 2;

    @Mock
    private CityRepository cityRepository;

    private CityMapper cityMapper = CityMapper.INSTANCE;

    @InjectMocks
    private CityService cityService;

    @Test
    void whenCityInformedThenItShouldBeCreated() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();
        City expectedCity = cityMapper.toModel(expectedCityDTO);

        // when
        when(cityRepository.findById(VALID_CITY_ID)).thenReturn(Optional.empty());
        when(cityRepository.save(expectedCity)).thenReturn(expectedCity);

        // then
        CityDTO createdCityDTO = cityService.insert(expectedCityDTO);

        assertThat(createdCityDTO, is(equalTo(expectedCityDTO)));
    }

    @Test
    void whenAlreadyRegisteredCityInformedThenAnExceptionShouldBeThrown() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();
        City expectedCity = cityMapper.toModel(expectedCityDTO);

        // when
        when(cityRepository.findById(VALID_CITY_ID)).thenReturn(Optional.of(expectedCity));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> cityService.insert(expectedCityDTO));
    }

    @Test
    void whenValidCityIdIsGivenThenReturnAnCity() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();
        City expectedCity = cityMapper.toModel(expectedCityDTO);

        // when
        when(cityRepository.findById(VALID_CITY_ID)).thenReturn(Optional.of(expectedCity));

        // then
        CityDTO foundCityDTO = cityService.find(VALID_CITY_ID);

        assertThat(foundCityDTO, is(equalTo(expectedCityDTO)));
    }

    @Test
    void whenNotRegisteredCityIdGivenThenThrowAnException() {
        // when
        when(cityRepository.findById(INVALID_CITY_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> cityService.find(INVALID_CITY_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidCityGivenThenReturnACityUpdated() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();
        City expectedCity = cityMapper.toModel(expectedCityDTO);

        // when
        when(cityRepository.findById(VALID_CITY_ID)).thenReturn(Optional.of(expectedCity));
        when(cityRepository.save(expectedCity)).thenReturn(expectedCity);

        // then
        CityDTO updateCityDTO = cityService.update(expectedCityDTO);

        assertThat(updateCityDTO, is(equalTo(expectedCityDTO)));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingCityThenAnExceptionShouldBeThrown() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();

        // when
        when(cityRepository.findById(VALID_CITY_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> cityService.update(expectedCityDTO));
    }

    @Test
    void whenListCityIsCalledThenReturnAListOfCity() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();
        City expectedCity = cityMapper.toModel(expectedCityDTO);

        // when
        when(cityRepository.findAll()).thenReturn(Collections.singletonList(expectedCity));

        // then
        List<CityDTO> listCityDTO = cityService.findAll();

        assertThat(listCityDTO, is(not(empty())));
        assertThat(listCityDTO.get(0), is(equalTo(expectedCityDTO)));
    }

    @Test
    void whenListCityIsCalledThenReturnAnEmptyListOfCity() {
        // when
        when(cityRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<CityDTO> listCityDTO = cityService.findAll();

        assertThat(listCityDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenACityShouldBeDeleted() {
        // given
        CityDTO expectedCityDTO = CityDTOBuilder.builder().build().toCityDTO();
        City expectedCity = cityMapper.toModel(expectedCityDTO);

        //  when
        when(cityRepository.findById(VALID_CITY_ID)).thenReturn(Optional.of(expectedCity));
        doNothing().when(cityRepository).deleteById(VALID_CITY_ID);

        // then
        cityService.delete(VALID_CITY_ID);

        verify(cityRepository, times(1)).findById(VALID_CITY_ID);
        verify(cityRepository, times(1)).deleteById(VALID_CITY_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingCityThenAnExceptionShouldBeThrown() {
        // when
        when(cityRepository.findById(INVALID_CITY_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> cityService.delete(INVALID_CITY_ID));
    }
}
