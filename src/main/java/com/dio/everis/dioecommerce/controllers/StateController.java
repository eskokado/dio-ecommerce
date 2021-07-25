package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.dto.StateDTO;
import com.dio.everis.dioecommerce.services.StateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateController {
    private StateService stateService;

    @GetMapping
    public List<StateDTO> findAll() {
        return stateService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDTO> find(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok().body(stateService.find(id));
    }

    @PostMapping
    public ResponseEntity<StateDTO> insert(@RequestBody @Valid StateDTO objDto) {
        objDto = stateService.insert(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(objDto.getId()).toUri();
        return ResponseEntity.created(uri).body(objDto);
    }

    @PutMapping
    public ResponseEntity<StateDTO> update(@RequestBody @Valid StateDTO objDto) {
        return ResponseEntity.ok().body(stateService.update(objDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
