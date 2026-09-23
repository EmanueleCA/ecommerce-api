package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.entity.OrderItem;
import com.emanuele.ecommerce_api.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderItemController {

    private final OrderItemService orderItemService;
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/order-items")
    public ResponseEntity<List<OrderItem>> findAll() {
        return ResponseEntity.ok(orderItemService.findAll());
    }

    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> findById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.findById(id);
        return ResponseEntity.ok().body(orderItem);
    }

    @PostMapping("/order-items")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItem save (@RequestBody OrderItem orderItem) {
        return orderItemService.save(orderItem);
    }

    @PutMapping("/order-items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItem update(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemService.save(orderItem);
    }

    @DeleteMapping("/order-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        orderItemService.deleteById(id);
    }
}
