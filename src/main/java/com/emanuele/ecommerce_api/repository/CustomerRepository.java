package com.emanuele.ecommerce_api.repository;

import com.emanuele.ecommerce_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
