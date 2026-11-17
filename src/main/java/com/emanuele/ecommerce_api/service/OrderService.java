package com.emanuele.ecommerce_api.service;

import com.emanuele.ecommerce_api.entity.Order;
import com.emanuele.ecommerce_api.entity.OrderItem;
import com.emanuele.ecommerce_api.entity.OrderStatus;
import com.emanuele.ecommerce_api.entity.Product;
import com.emanuele.ecommerce_api.exception.InsufficientStockException;
import com.emanuele.ecommerce_api.exception.InvalidOrderStateException;
import com.emanuele.ecommerce_api.exception.ResourceNotFoundException;
import com.emanuele.ecommerce_api.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Transactional
    public Order save(Order order){
        BigDecimal sum = BigDecimal.ZERO;
        for(OrderItem item : order.getOrderItems()){
            Product product = productService.findById(item.getProduct().getId());// pegar o produto com suas informações
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());

            if (item.getQuantity() <= product.getStockQuantity()) {
                Integer stockQuantity = product.getStockQuantity();
                stockQuantity -= item.getQuantity();
                product.setStockQuantity(stockQuantity);
                productService.save(product);
            }else{
                throw new InsufficientStockException("Insufficient Stock quantity");
            }

            item.setOrder(order);
            //calculate the totalValue automatically
            BigDecimal unitPrice = item.getUnitPrice(); //pega o preço unitário
            int quantity = item.getQuantity(); //pega a qnt de itens
            BigDecimal num = BigDecimal.valueOf(quantity); //transforma quantity de Integer para BigDecimal
            BigDecimal mult = num.multiply(unitPrice);
            sum = sum.add(mult);
        }
        order.setTotalValue(sum);
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long id){
        Order order = findById(id);
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException("Order is already cancelled");
        }
        for(OrderItem item : order.getOrderItems()){
            Product product = productService.findById(item.getProduct().getId());
            Integer stockQuantity = product.getStockQuantity();
            stockQuantity += item.getQuantity();
            product.setStockQuantity(stockQuantity);
            productService.save(product);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }


    public void deleteById(Long id){
        Order order = findById(id);
        if (order.getOrderStatus() != OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException("Only cancelled orders can be deleted");
        }
        orderRepository.deleteById(id);

    }
}
