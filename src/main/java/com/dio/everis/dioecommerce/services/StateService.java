package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.StateDTO;
import com.dio.everis.dioecommerce.entities.State;
import com.dio.everis.dioecommerce.mappers.StateMapper;
import com.dio.everis.dioecommerce.repositories.StateRepository;
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
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper = StateMapper.INSTANCE;

    public StateDTO insert(StateDTO objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        State objToSave = stateMapper.toModel(objDTO);
        State objSaved = stateRepository.save(objToSave);
        return stateMapper.toDto(objSaved);
    }

    public StateDTO find(Integer id) {
        State obj = stateRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + State.class.getName()
                ));
        return stateMapper.toDto(obj);
    }

    public StateDTO update(StateDTO objDto) {
        find(objDto.getId());
        State objToSave = stateMapper.toModel(objDto);
        State objSaved = stateRepository.save(objToSave);
        return stateMapper.toDto(objSaved);
    }

    public List<StateDTO> findAll() {
        return stateRepository.findAll().stream().map(stateMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Integer id) {
        Optional<State> optObj = stateRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + State.class.getName()
            );
        }
    }

    public void delete(Integer id) {
        find(id);
        try {
            stateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um estado que possui dependências");
        }
    }
}
