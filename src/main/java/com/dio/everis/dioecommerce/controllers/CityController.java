package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.dto.CityDTO;
import com.dio.everis.dioecommerce.services.CityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CityController {
    private CityService cityService;

    @GetMapping
    public List<CityDTO> findAll() {
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> find(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok().body(cityService.find(id));
    }

    @PostMapping
    public ResponseEntity<CityDTO> insert(@RequestBody @Valid CityDTO objDto) {
        objDto = cityService.insert(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(objDto.getId()).toUri();
        return ResponseEntity.created(uri).body(objDto);
    }

    @PutMapping
    public ResponseEntity<CityDTO> update(@RequestBody @Valid CityDTO objDto) {
        return ResponseEntity.ok().body(cityService.update(objDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
