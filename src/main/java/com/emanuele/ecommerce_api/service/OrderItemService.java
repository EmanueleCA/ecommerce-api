package com.emanuele.ecommerce_api.service;

import com.emanuele.ecommerce_api.entity.OrderItem;
import com.emanuele.ecommerce_api.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    public  OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findAll(){
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Long id){
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item not found"));
    }

    public OrderItem save(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    public void deleteById(Long id){
        orderItemRepository.deleteById(id);
    }
}

