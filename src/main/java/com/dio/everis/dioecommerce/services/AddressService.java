package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.AddressDTO;
import com.dio.everis.dioecommerce.entities.Address;
import com.dio.everis.dioecommerce.mappers.AddressMapper;
import com.dio.everis.dioecommerce.repositories.AddressRepository;
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
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper = AddressMapper.INSTANCE;

    public AddressDTO insert(AddressDTO objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        Address objToSave = addressMapper.toModel(objDTO);
        Address objSaved = addressRepository.save(objToSave);
        return addressMapper.toDto(objSaved);
    }

    public AddressDTO find(Long id) {
        Address obj = addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Address.class.getName()
                ));
        return addressMapper.toDto(obj);
    }

    public AddressDTO update(AddressDTO objDto) {
        find(objDto.getId());
        Address objToSave = addressMapper.toModel(objDto);
        Address objSaved = addressRepository.save(objToSave);
        return addressMapper.toDto(objSaved);
    }

    public List<AddressDTO> findAll() {
        return addressRepository.findAll().stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Long id) {
        Optional<Address> optObj = addressRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + Address.class.getName()
            );
        }
    }

    public void delete(Long id) {
        find(id);
        try {
            addressRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um endereço que possui dependências");
        }
    }
}
