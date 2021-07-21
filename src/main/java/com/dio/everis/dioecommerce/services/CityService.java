package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.entities.City;
import com.dio.everis.dioecommerce.mappers.CityMapper;
import com.dio.everis.dioecommerce.repositories.CityRepository;
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
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper = CityMapper.INSTANCE;

    public CityDTO insert(CityDTO objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        City objToSave = cityMapper.toModel(objDTO);
        City objSaved = cityRepository.save(objToSave);
        return cityMapper.toDto(objSaved);
    }

    public CityDTO find(Integer id) {
        City obj = cityRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + City.class.getName()
                ));
        return cityMapper.toDto(obj);
    }

    public CityDTO update(CityDTO objDto) {
        find(objDto.getId());
        City objToSave = cityMapper.toModel(objDto);
        City objSaved = cityRepository.save(objToSave);
        return cityMapper.toDto(objSaved);
    }

    public List<CityDTO> findAll() {
        return cityRepository.findAll().stream().map(cityMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Integer id) {
        Optional<City> optObj = cityRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + City.class.getName()
            );
        }
    }

    public void delete(Integer id) {
        find(id);
        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cidade que possui dependências");
        }
    }
}
