package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.dto.AddressRequestDTO;
import com.emanuele.ecommerce_api.dto.AddressResponseDTO;
import com.emanuele.ecommerce_api.entity.Address;
import com.emanuele.ecommerce_api.entity.Customer;
import com.emanuele.ecommerce_api.service.AddressService;
import com.emanuele.ecommerce_api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;
    private final CustomerService customerService;
    public AddressController(AddressService addressService, CustomerService customerService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }
    private Address toEntity(AddressRequestDTO requestDTO, Address address) {
        Customer customer = customerService.findById(requestDTO.getCustomerId());

        address.setStreet(requestDTO.getStreet());
        address.setNumber(requestDTO.getNumber());
        address.setComplement(requestDTO.getComplement());
        address.setCity(requestDTO.getCity());
        address.setState(requestDTO.getState());
        address.setPostalCode(requestDTO.getPostalCode());
        address.setCustomer(customer);

        return address;
    }

    private AddressResponseDTO toResponse(Address address) {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setId(address.getId());
        responseDTO.setStreet(address.getStreet());
        responseDTO.setNumber(address.getNumber());
        responseDTO.setComplement(address.getComplement());
        responseDTO.setCity(address.getCity());
        responseDTO.setState(address.getState());
        responseDTO.setPostalCode(address.getPostalCode());
        responseDTO.setCustomerId(address.getCustomer().getId());

        return responseDTO;
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponseDTO>> findAll(){
        List<AddressResponseDTO> responseList = new ArrayList<>();

        for (Address address : addressService.findAll()) {
            responseList.add(toResponse(address));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable Long id){
        Address address = addressService.findById(id);
        return new ResponseEntity<>(toResponse(address), HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponseDTO> save(@Valid @RequestBody AddressRequestDTO requestDTO){
        Address address = toEntity(requestDTO,new Address());
        Address savedAddress = addressService.save(address);
        return new ResponseEntity<>(toResponse(savedAddress), HttpStatus.CREATED);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AddressRequestDTO requestDTO){
        Address address = addressService.findById(id);
        Address update = addressService.save(toEntity(requestDTO,address));

        return new ResponseEntity<>(toResponse(update), HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        addressService.deleteById(id);
    }
}
