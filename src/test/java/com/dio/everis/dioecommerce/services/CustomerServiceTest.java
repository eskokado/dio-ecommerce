package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.CustomerDTOBuilder;
import com.dio.everis.dioecommerce.dto.CustomerDTO;
import com.dio.everis.dioecommerce.entities.Customer;
import com.dio.everis.dioecommerce.mappers.CustomerMapper;
import com.dio.everis.dioecommerce.repositories.CustomerRepository;
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
public class CustomerServiceTest {
    private static final Long VALID_CUSTOMER_ID = 1L;
    private static final Long INVALID_CUSTOMER_ID = 2L;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void whenCustomerInformedThenItShouldBeCreated() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        Customer expectedCustomer = customerMapper.toModel(expectedCustomerDTO);

        // when
        when(customerRepository.findById(VALID_CUSTOMER_ID)).thenReturn(Optional.empty());
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        // then
        CustomerDTO createdCustomerDTO = customerService.insert(expectedCustomerDTO);

        assertThat(createdCustomerDTO, is(equalTo(expectedCustomerDTO)));
    }

    @Test
    void whenAlreadyRegisteredCustomerInformedThenAnExceptionShouldBeThrown() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        Customer expectedCustomer = customerMapper.toModel(expectedCustomerDTO);

        // when
        when(customerRepository.findById(VALID_CUSTOMER_ID)).thenReturn(Optional.of(expectedCustomer));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> customerService.insert(expectedCustomerDTO));
    }

    @Test
    void whenValidCustomerIdIsGivenThenReturnAnCustomer() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        Customer expectedCustomer = customerMapper.toModel(expectedCustomerDTO);

        // when
        when(customerRepository.findById(VALID_CUSTOMER_ID)).thenReturn(Optional.of(expectedCustomer));

        // then
        CustomerDTO foundCustomerDTO = customerService.find(VALID_CUSTOMER_ID);

        assertThat(foundCustomerDTO, is(equalTo(expectedCustomerDTO)));
    }

    @Test
    void whenNotRegisteredCustomerIdGivenThenThrowAnException() {
        // when
        when(customerRepository.findById(INVALID_CUSTOMER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> customerService.find(INVALID_CUSTOMER_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidCustomerGivenThenReturnACustomerUpdated() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        Customer expectedCustomer = customerMapper.toModel(expectedCustomerDTO);

        // when
        when(customerRepository.findById(VALID_CUSTOMER_ID)).thenReturn(Optional.of(expectedCustomer));
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        // then
        CustomerDTO updateCustomerDTO = customerService.update(expectedCustomerDTO);

        assertThat(updateCustomerDTO, is(equalTo(expectedCustomerDTO)));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingCustomerThenAnExceptionShouldBeThrown() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();

        // when
        when(customerRepository.findById(VALID_CUSTOMER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> customerService.update(expectedCustomerDTO));
    }

    @Test
    void whenListCustomerIsCalledThenReturnAListOfCustomer() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        Customer expectedCustomer = customerMapper.toModel(expectedCustomerDTO);

        // when
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(expectedCustomer));

        // then
        List<CustomerDTO> listCustomerDTO = customerService.findAll();

        assertThat(listCustomerDTO, is(not(empty())));
        assertThat(listCustomerDTO.get(0), is(equalTo(expectedCustomerDTO)));
    }

    @Test
    void whenListCustomerIsCalledThenReturnAnEmptyListOfCustomer() {
        // when
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<CustomerDTO> listCustomerDTO = customerService.findAll();

        assertThat(listCustomerDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenACustomerShouldBeDeleted() {
        // given
        CustomerDTO expectedCustomerDTO = CustomerDTOBuilder.builder().build().toCustomerDTO();
        Customer expectedCustomer = customerMapper.toModel(expectedCustomerDTO);

        //  when
        when(customerRepository.findById(VALID_CUSTOMER_ID)).thenReturn(Optional.of(expectedCustomer));
        doNothing().when(customerRepository).deleteById(VALID_CUSTOMER_ID);

        // then
        customerService.delete(VALID_CUSTOMER_ID);

        verify(customerRepository, times(1)).findById(VALID_CUSTOMER_ID);
        verify(customerRepository, times(1)).deleteById(VALID_CUSTOMER_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingCustomerThenAnExceptionShouldBeThrown() {
        // when
        when(customerRepository.findById(INVALID_CUSTOMER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> customerService.delete(INVALID_CUSTOMER_ID));
    }
}
