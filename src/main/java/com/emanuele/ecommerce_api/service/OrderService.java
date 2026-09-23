package com.emanuele.ecommerce_api.service;

import com.emanuele.ecommerce_api.entity.Order;
import com.emanuele.ecommerce_api.entity.OrderItem;
import com.emanuele.ecommerce_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order save(Order order){
        for(OrderItem item : order.getOrderItems()){
            item.setOrder(order);
        }
        return orderRepository.save(order);
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }
}
