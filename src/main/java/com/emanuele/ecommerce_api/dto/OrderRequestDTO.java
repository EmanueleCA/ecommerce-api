package com.emanuele.ecommerce_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    @NotNull(message = "must not be null")
    private Long customerId;
    @NotEmpty(message = "must not be empty")
    @Valid
    private List<OrderItemRequestDTO> items;
}
