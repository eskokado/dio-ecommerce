package com.dio.everis.dioecommerce.services;

import com.dio.everis.dioecommerce.dto.ProductDTO;
import com.dio.everis.dioecommerce.entities.Product;
import com.dio.everis.dioecommerce.mappers.ProductMapper;
import com.dio.everis.dioecommerce.repositories.ProductRepository;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ProductDTO insert(ProductDTO objDTO) {
        verifyIfIsAlreadyRegistered(objDTO.getId());
        Product objToSave = productMapper.toModel(objDTO);
        Product objSaved = productRepository.save(objToSave);
        return productMapper.toDto(objSaved);
    }

    public ProductDTO find(Long id) {
        Product obj = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()
                ));
        return productMapper.toDto(obj);
    }

    public ProductDTO update(ProductDTO objDto) {
        find(objDto.getId());
        Product objToSave = productMapper.toModel(objDto);
        Product objSaved = productRepository.save(objToSave);
        return productMapper.toDto(objSaved);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    private void verifyIfIsAlreadyRegistered(Long id) {
        Optional<Product> optObj = productRepository.findById(id);
        if (optObj.isPresent()) {
            throw new ObjectAlreadyRegisteredException(
                    "Objeto existente! Id: " + id + ", Tipo: " + Product.class.getName()
            );
        }
    }

    public void delete(Long id) {
        find(id);
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um produto que possui dependências");
        }
    }
}
