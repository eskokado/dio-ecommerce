package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.builders.OrderDTOBuilder;
import com.dio.everis.dioecommerce.builders.OrderWithPaymentCardDTOBuilder;
import com.dio.everis.dioecommerce.builders.OrderWithPaymentSlipDTOBuilder;
import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.dto.OrderWithPaymentCardDTO;
import com.dio.everis.dioecommerce.dto.OrderWithPaymentSlipDTO;
import com.dio.everis.dioecommerce.entities.Order;
import com.dio.everis.dioecommerce.mappers.OrderMapper;
import com.dio.everis.dioecommerce.repositories.OrderRepository;
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
public class OrderServiceTest {
    private static final Long VALID_ORDER_ID = 1L;
    private static final Long INVALID_ORDER_ID = 2L;

    @Mock
    private OrderRepository orderRepository;

    private OrderMapper orderMapper = OrderMapper.INSTANCE;

    @InjectMocks
    private OrderService orderService;

    @Test
    void whenOrderWithPaymentCardInformedThenItShouldBeCreated() {
        // given
        OrderWithPaymentCardDTO expectedOrderDTO = OrderWithPaymentCardDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.empty());
        when(orderRepository.save(expectedOrder)).thenReturn(expectedOrder);

        // then
        OrderWithPaymentCardDTO createdOrderDTO = orderService.insert(expectedOrderDTO);

        assertThat(createdOrderDTO, is(equalTo(expectedOrderDTO)));
    }

    @Test
    void whenOrderWithPaymentSlipInformedThenItShouldBeCreated() {
        // given
        OrderWithPaymentSlipDTO expectedOrderDTO = OrderWithPaymentSlipDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.empty());
        when(orderRepository.save(expectedOrder)).thenReturn(expectedOrder);

        // then
        OrderWithPaymentSlipDTO createdOrderDTO = orderService.insert(expectedOrderDTO);

        assertThat(createdOrderDTO, is(equalTo(expectedOrderDTO)));
    }

    @Test
    void whenAlreadyRegisteredOrderWithPaymentCardInformedThenAnExceptionShouldBeThrown() {
        // given
        OrderWithPaymentCardDTO expectedOrderDTO = OrderWithPaymentCardDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(expectedOrder));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> orderService.insert(expectedOrderDTO));
    }

    @Test
    void whenAlreadyRegisteredOrderWithPaymentSlipInformedThenAnExceptionShouldBeThrown() {
        // given
        OrderWithPaymentSlipDTO expectedOrderDTO = OrderWithPaymentSlipDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(expectedOrder));

        // then
        assertThrows(ObjectAlreadyRegisteredException.class, () -> orderService.insert(expectedOrderDTO));
    }

    @Test
    void whenValidOrderIdIsGivenThenReturnAnOrder() {
        // given
        OrderDTO expectedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(expectedOrder));

        // then
        OrderDTO foundOrderDTO = orderService.find(VALID_ORDER_ID);

        assertThat(foundOrderDTO, is(equalTo(expectedOrderDTO)));
    }

    @Test
    void whenNotRegisteredOrderIdGivenThenThrowAnException() {
        // when
        when(orderRepository.findById(INVALID_ORDER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> orderService.find(INVALID_ORDER_ID));
    }

    @Test
    void whenUpdateIsCalledWithValidOrderWithPaymentCardGivenThenReturnAOrderUpdated() {
        // given
        OrderWithPaymentCardDTO expectedOrderDTO = OrderWithPaymentCardDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(expectedOrder));
        when(orderRepository.save(expectedOrder)).thenReturn(expectedOrder);

        // then
        OrderWithPaymentCardDTO updateOrderDTO = orderService.update(expectedOrderDTO);

        assertThat(updateOrderDTO, is(equalTo(expectedOrderDTO)));
    }

    @Test
    void whenUpdateIsCalledWithValidOrderWithPaymentSlipGivenThenReturnAOrderUpdated() {
        // given
        OrderWithPaymentSlipDTO expectedOrderDTO = OrderWithPaymentSlipDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(expectedOrder));
        when(orderRepository.save(expectedOrder)).thenReturn(expectedOrder);

        // then
        OrderWithPaymentSlipDTO updateOrderDTO = orderService.update(expectedOrderDTO);

        assertThat(updateOrderDTO, is(equalTo(expectedOrderDTO)));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingOrderWithPaymentCardThenAnExceptionShouldBeThrown() {
        // given
        OrderWithPaymentCardDTO expectedOrderDTO = OrderWithPaymentCardDTOBuilder.builder().build().toOrderDTO();

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> orderService.update(expectedOrderDTO));
    }

    @Test
    void whenUpdateIsCalledWithNotExistingOrderWithPaymentSlipThenAnExceptionShouldBeThrown() {
        // given
        OrderWithPaymentSlipDTO expectedOrderDTO = OrderWithPaymentSlipDTOBuilder.builder().build().toOrderDTO();

        // when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> orderService.update(expectedOrderDTO));
    }

    @Test
    void whenListOrderIsCalledThenReturnAListOfOrder() {
        // given
        OrderDTO expectedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(expectedOrder));

        // then
        List<OrderDTO> listOrderDTO = orderService.findAll();

        assertThat(listOrderDTO, is(not(empty())));
        assertThat(listOrderDTO.get(0), is(equalTo(expectedOrderDTO)));
    }

    @Test
    void whenListOrderIsCalledThenReturnAnEmptyListOfOrder() {
        // when
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<OrderDTO> listOrderDTO = orderService.findAll();

        assertThat(listOrderDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAOrderShouldBeDeleted() {
        // given
        OrderDTO expectedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);

        //  when
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(expectedOrder));
        doNothing().when(orderRepository).deleteById(VALID_ORDER_ID);

        // then
        orderService.delete(VALID_ORDER_ID);

        verify(orderRepository, times(1)).findById(VALID_ORDER_ID);
        verify(orderRepository, times(1)).deleteById(VALID_ORDER_ID);
    }

    @Test
    void whenExclusionIsCalledWithNotExistingOrderThenAnExceptionShouldBeThrown() {
        // when
        when(orderRepository.findById(INVALID_ORDER_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> orderService.delete(INVALID_ORDER_ID));
    }
}
