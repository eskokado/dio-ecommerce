package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.dto.CategoryDTO;
import com.dio.everis.dioecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> find(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(categoryService.find(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody @Valid CategoryDTO objDto) {
        objDto = categoryService.insert(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(objDto.getId()).toUri();
        return ResponseEntity.created(uri).body(objDto);
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> update(@RequestBody @Valid CategoryDTO objDto) {
        return ResponseEntity.ok().body(categoryService.update(objDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
