package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.dto.AddressResponseDTO;
import com.emanuele.ecommerce_api.dto.CustomerRequestDTO;
import com.emanuele.ecommerce_api.dto.CustomerResponseDTO;
import com.emanuele.ecommerce_api.entity.Address;
import com.emanuele.ecommerce_api.entity.Customer;
import com.emanuele.ecommerce_api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private CustomerResponseDTO toResponse(Customer customer) {
        CustomerResponseDTO customerDTO = new CustomerResponseDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setRegistrationDate(customer.getRegistrationDate());


        List<AddressResponseDTO> addresses = new ArrayList<>();

        for (Address address : customer.getAddresses()){
            AddressResponseDTO dto = new AddressResponseDTO();
            dto.setId(address.getId());
            dto.setStreet(address.getStreet());
            dto.setNumber(address.getNumber());
            dto.setComplement(address.getComplement());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setPostalCode(address.getPostalCode());
            dto.setCustomerId(customer.getId());

            addresses.add(dto);
        }
        customerDTO.setAddresses(addresses);
        return customerDTO;
    }

    private Customer toEntity(CustomerRequestDTO requestDTO, Customer customer) {
        customer.setName(requestDTO.getName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPhone(requestDTO.getPhone());
        customer.setPassword(requestDTO.getPassword());

        return customer;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        List<CustomerResponseDTO> responseList = new ArrayList<>();

        for (Customer customer : customerService.findAll()) {
            responseList.add(toResponse(customer));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        return new ResponseEntity<>(toResponse(customer), HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseDTO> save(@Valid @RequestBody CustomerRequestDTO requestDTO){
        Customer customer = toEntity(requestDTO, new Customer());
        Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<>(toResponse(savedCustomer), HttpStatus.CREATED);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO requestDTO){
        Customer customer = customerService.findById(id);
        Customer update = customerService.save(toEntity(requestDTO, customer));

        return new ResponseEntity<>(toResponse(update), HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        customerService.deleteById(id);
    }
}
