package com.emanuele.ecommerce_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    @Positive
    @NotNull(message = "must not be null")
    private Integer quantity;
    @NotNull(message = "must not be null")
    private Long productId;
}
