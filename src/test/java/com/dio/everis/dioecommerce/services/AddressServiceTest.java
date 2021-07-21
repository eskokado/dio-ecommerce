package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.AddressDTOBuilder;
import com.dio.everis.dioecommerce.dto.AddressDTO;
import com.dio.everis.dioecommerce.entities.Address;
import com.dio.everis.dioecommerce.mappers.AddressMapper;
import com.dio.everis.dioecommerce.repositories.AddressRepository;
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
public class AddressServiceTest {
    private static final Long VALID_ADDRESS_ID = 1L;
    private static final Long INVALID_ADDRESS_ID = 2L;

    @Mock
    private AddressRepository addressRepository;

    private AddressMapper addressMapper = AddressMapper.INSTANCE;

    @InjectMocks
    private AddressService addressService;

    @Test
    void whenAddressInformedThenItShouldBeCreated() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        Address expectedAddress = addressMapper.toModel(expectedAddressDTO);

        // when
        when(addressRepository.findById(VALID_ADDRESS_ID)).thenReturn(Optional.empty());
        when(addressRepository.save(expectedAddress)).thenReturn(expectedAddress);

        // then
        AddressDTO createdAddressDTO = addressService.insert(expectedAddressDTO);

        assertThat(createdAddressDTO, is(equalTo(expectedAddressDTO)));
    }

    @Test
    void whenAlreadyRegisteredAddressInformedThenAnExceptionShouldBeThrown() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        Address expectedAddress = addressMapper.toModel(expectedAddressDTO);

        // when
        when(addressRepository.findById(VALID_ADDRESS_ID)).thenReturn(Optional.of(expectedAddress));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> addressService.insert(expectedAddressDTO));
    }

    @Test
    void whenValidAddressIdIsGivenThenReturnAnAddress() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        Address expectedAddress = addressMapper.toModel(expectedAddressDTO);

        // when
        when(addressRepository.findById(VALID_ADDRESS_ID)).thenReturn(Optional.of(expectedAddress));

        // then
        AddressDTO foundAddressDTO = addressService.find(VALID_ADDRESS_ID);

        assertThat(foundAddressDTO, is(equalTo(expectedAddressDTO)));
    }

    @Test
    void whenNotRegisteredAddressIdGivenThenThrowAnException() {
        // when
        when(addressRepository.findById(INVALID_ADDRESS_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> addressService.find(INVALID_ADDRESS_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidAddressGivenThenReturnAAddressUpdated() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        Address expectedAddress = addressMapper.toModel(expectedAddressDTO);

        // when
        when(addressRepository.findById(VALID_ADDRESS_ID)).thenReturn(Optional.of(expectedAddress));
        when(addressRepository.save(expectedAddress)).thenReturn(expectedAddress);

        // then
        AddressDTO updateAddressDTO = addressService.update(expectedAddressDTO);

        assertThat(updateAddressDTO, is(equalTo(expectedAddressDTO)));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingAddressThenAnExceptionShouldBeThrown() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();

        // when
        when(addressRepository.findById(VALID_ADDRESS_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> addressService.update(expectedAddressDTO));
    }

    @Test
    void whenListAddressIsCalledThenReturnAListOfAddress() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        Address expectedAddress = addressMapper.toModel(expectedAddressDTO);

        // when
        when(addressRepository.findAll()).thenReturn(Collections.singletonList(expectedAddress));

        // then
        List<AddressDTO> listAddressDTO = addressService.findAll();

        assertThat(listAddressDTO, is(not(empty())));
        assertThat(listAddressDTO.get(0), is(equalTo(expectedAddressDTO)));
    }

    @Test
    void whenListAddressIsCalledThenReturnAnEmptyListOfAddress() {
        // when
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<AddressDTO> listAddressDTO = addressService.findAll();

        assertThat(listAddressDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAAddressShouldBeDeleted() {
        // given
        AddressDTO expectedAddressDTO = AddressDTOBuilder.builder().build().toAddressDTO();
        Address expectedAddress = addressMapper.toModel(expectedAddressDTO);

        //  when
        when(addressRepository.findById(VALID_ADDRESS_ID)).thenReturn(Optional.of(expectedAddress));
        doNothing().when(addressRepository).deleteById(VALID_ADDRESS_ID);

        // then
        addressService.delete(VALID_ADDRESS_ID);

        verify(addressRepository, times(1)).findById(VALID_ADDRESS_ID);
        verify(addressRepository, times(1)).deleteById(VALID_ADDRESS_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingAddressThenAnExceptionShouldBeThrown() {
        // when
        when(addressRepository.findById(INVALID_ADDRESS_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> addressService.delete(INVALID_ADDRESS_ID));
    }
}
