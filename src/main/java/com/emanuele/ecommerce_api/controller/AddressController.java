package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.entity.Address;
import com.emanuele.ecommerce_api.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id){
        Address address = addressService.findById(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping("/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public Address save(@RequestBody Address address){
        return addressService.save(address);
    }

    @PutMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address update(@PathVariable Long id, @RequestBody Address address){
        address.setId(id);
        return addressService.save(address);
    }

    @DeleteMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        addressService.deleteById(id);
    }
}
