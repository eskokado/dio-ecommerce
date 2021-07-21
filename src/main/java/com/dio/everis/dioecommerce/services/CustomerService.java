package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto. CustomerWithoutAddressesDTO ;
import com.dio.everis.dioecommerce.dto.CustomerWithoutAddressesDTO;
import com.dio.everis.dioecommerce.entities.Customer;
import com.dio.everis.dioecommerce.mappers.CustomerMapper;
import com.dio.everis.dioecommerce.repositories.CustomerRepository;
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
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public  CustomerWithoutAddressesDTO  insert( CustomerWithoutAddressesDTO  objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        Customer objToSave = customerMapper.toModel(objDTO);
        Customer objSaved = customerRepository.save(objToSave);
        return customerMapper.toDto(objSaved);
    }

    public CustomerWithoutAddressesDTO find(Long id) {
        Customer obj = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Customer.class.getName()
                ));
        return customerMapper.toDto(obj);
    }

    public  CustomerWithoutAddressesDTO  update( CustomerWithoutAddressesDTO  objDto) {
        find(objDto.getId());
        Customer objToSave = customerMapper.toModel(objDto);
        Customer objSaved = customerRepository.save(objToSave);
        return customerMapper.toDto(objSaved);
    }

    public List< CustomerWithoutAddressesDTO > findAll() {
        return customerRepository.findAll().stream().map(customerMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Long id) {
        Optional<Customer> optObj = customerRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + Customer.class.getName()
            );
        }
    }

    public void delete(Long id) {
        find(id);
        try {
            customerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cliente que possui dependências");
        }
    }
}
