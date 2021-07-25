package com.dio.everis.dioecommerce.controllers;

import com.dio.everis.dioecommerce.dto.CustomerDTO;
import com.dio.everis.dioecommerce.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> find(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(customerService.find(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> insert(@RequestBody @Valid CustomerDTO objDto) {
        objDto = customerService.insert(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(objDto.getId()).toUri();
        return ResponseEntity.created(uri).body(objDto);
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> update(@RequestBody @Valid CustomerDTO objDto) {
        return ResponseEntity.ok().body(customerService.update(objDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
