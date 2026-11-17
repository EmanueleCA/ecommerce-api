package com.emanuele.ecommerce_api.controller;

import com.emanuele.ecommerce_api.dto.OrderItemRequestDTO;
import com.emanuele.ecommerce_api.dto.OrderItemResponseDTO;
import com.emanuele.ecommerce_api.dto.OrderRequestDTO;
import com.emanuele.ecommerce_api.dto.OrderResponseDTO;
import com.emanuele.ecommerce_api.entity.Order;
import com.emanuele.ecommerce_api.entity.OrderItem;
import com.emanuele.ecommerce_api.entity.Product;
import com.emanuele.ecommerce_api.service.CustomerService;
import com.emanuele.ecommerce_api.service.OrderService;
import com.emanuele.ecommerce_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    public OrderController(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    private Order toEntity(OrderRequestDTO requestDTO) {
        Order order = new Order();
        order.setCustomer(customerService.findById(requestDTO.getCustomerId()));

        for (OrderItemRequestDTO itemDTO : requestDTO.getItems()) {
            OrderItem item = new OrderItem();
            Product product = productService.findById(itemDTO.getProductId());// 1. buscar o Product com productService.findById(...) e dar setProduct
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());// 2. dar setQuantity com a quantidade do itemDTO
            order.addItem(item);   // já faz o setOrder e adiciona na lista
        }
        return order;
    }

    private OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalValue(order.getTotalValue());

        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setUnitPrice(item.getUnitPrice());
            items.add(itemDTO);
        }
        dto.setItems(items);
        return dto;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> findAll(){
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orderService.findAll()){
            responseList.add(toResponse(order));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id){
        Order order = orderService.findById(id);
        return new ResponseEntity<>(toResponse(order), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> save(@Valid @RequestBody OrderRequestDTO requestDTO){
        Order order = toEntity(requestDTO);
        Order saved = orderService.save(order);
        return new ResponseEntity<>(toResponse(saved), HttpStatus.CREATED);
    }

    @PatchMapping("/orders/{id}/cancel") //atualização parcial
    public ResponseEntity<OrderResponseDTO> cancel(@PathVariable Long id){
        return new ResponseEntity<>(toResponse(orderService.cancelOrder(id)), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        orderService.deleteById(id);
    }
}
