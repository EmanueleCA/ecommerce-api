package com.emanuele.ecommerce_api.service;

import com.emanuele.ecommerce_api.entity.Order;
import com.emanuele.ecommerce_api.entity.OrderItem;
import com.emanuele.ecommerce_api.entity.OrderStatus;
import com.emanuele.ecommerce_api.entity.Product;
import com.emanuele.ecommerce_api.exception.InsufficientStockException;
import com.emanuele.ecommerce_api.exception.ResourceNotFoundException;
import com.emanuele.ecommerce_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

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

    public Order save(Order order){
        BigDecimal sum = BigDecimal.ZERO;
        for(OrderItem item : order.getOrderItems()){
            Product product = productService.findById(item.getProduct().getId());// pegar o produto com suas informações
            item.setProduct(product);

            if (item.getQuantity() <= product.getStockQuantity()) {
                Integer stockQuantity = product.getStockQuantity();
                stockQuantity -= item.getQuantity();
                product.setStockQuantity(stockQuantity);
                productService.save(product);
            }else{
                throw new InsufficientStockException("Insuffficient Stock quantity");
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

    public Order cancelOrder(Long id){
        Order order = findById(id);
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
        orderRepository.deleteById(id);
    }
}
