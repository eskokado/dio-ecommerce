package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.dto.AddressDTO;
import com.dio.everis.dioecommerce.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AddressController {
    private AddressService addressService;

    @GetMapping
    public List<AddressDTO> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> find(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(addressService.find(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestBody @Valid AddressDTO objDto) {
        objDto = addressService.insert(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(objDto.getId()).toUri();
        return ResponseEntity.created(uri).body(objDto);
    }

    @PutMapping
    public ResponseEntity<AddressDTO> update(@RequestBody @Valid AddressDTO objDto) {
        return ResponseEntity.ok().body(addressService.update(objDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
