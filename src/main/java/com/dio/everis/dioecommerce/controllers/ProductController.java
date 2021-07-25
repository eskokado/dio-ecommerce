package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.dto.ProductDTO;
import com.dio.everis.dioecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> find(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(productService.find(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody @Valid ProductDTO objDto) {
        objDto = productService.insert(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(objDto.getId()).toUri();
        return ResponseEntity.created(uri).body(objDto);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO objDto) {
        return ResponseEntity.ok().body(productService.update(objDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
