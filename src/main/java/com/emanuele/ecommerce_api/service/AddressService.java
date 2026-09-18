package com.emanuele.ecommerce_api.service;

import com.emanuele.ecommerce_api.entity.Address;
import com.emanuele.ecommerce_api.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id){
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public Address save(Address address){
        return addressRepository.save(address);
    }

    public void deleteById(Long id){
        addressRepository.deleteById(id);
    }
}
