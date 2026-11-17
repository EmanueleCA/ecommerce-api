package com.emanuele.ecommerce_api.dto;

import com.emanuele.ecommerce_api.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private BigDecimal totalValue;
    private List<OrderItemResponseDTO> items;

}
