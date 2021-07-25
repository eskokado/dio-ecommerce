package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.OrderDTO;
import com.dio.everis.dioecommerce.entities.Order;
import com.dio.everis.dioecommerce.mappers.OrderMapper;
import com.dio.everis.dioecommerce.repositories.OrderRepository;
import com.dio.everis.dioecommerce.services.exceptions.DataIntegrityException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectAlreadyRegisteredException;
import com.dio.everis.dioecommerce.services.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    public OrderDTO insert(OrderDTO objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        Order objToSave = orderMapper.toModel(objDTO);
        Order objSaved = orderRepository.save(objToSave);
        return orderMapper.toDto(objSaved);
    }

    public OrderDTO find(Long id) {
        Order obj = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()
                ));
        return orderMapper.toDto(obj);
    }

    public OrderDTO update(OrderDTO objDto) {
        find(objDto.getId());
        Order objToSave = orderMapper.toModel(objDto);
        Order objSaved = orderRepository.save(objToSave);
        return orderMapper.toDto(objSaved);
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Long id) {
        Optional<Order> optObj = orderRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + Order.class.getName()
            );
        }
    }

    public void delete(Long id) {
        find(id);
        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um pedido que possui dependências");
        }
    }
}
