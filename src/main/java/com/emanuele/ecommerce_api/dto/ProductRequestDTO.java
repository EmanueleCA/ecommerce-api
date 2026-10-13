package com.emanuele.ecommerce_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    @NotBlank(message = "must not be blank")
    private String name;
    @NotBlank(message = "must not be blank")
    private String description;
    @NotNull(message = "must not be null")
    private BigDecimal price;
    @NotNull(message = "must not be null")
    private Integer stockQuantity;
    private Boolean active;
    @NotNull(message = "must not be null")
    private Long categoryId;
}
