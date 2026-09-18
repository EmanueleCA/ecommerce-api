package com.emanuele.ecommerce_api.repository;

import com.emanuele.ecommerce_api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
