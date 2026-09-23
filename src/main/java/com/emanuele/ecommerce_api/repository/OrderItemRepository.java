package com.emanuele.ecommerce_api.repository;

import com.emanuele.ecommerce_api.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
