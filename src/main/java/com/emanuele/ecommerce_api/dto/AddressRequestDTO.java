package com.emanuele.ecommerce_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {
    @NotBlank(message = "must not be blank")
    private String street;
    @NotNull(message = "must not be null")
    private Integer number;
    @NotBlank(message = "must not be blank")
    private String complement;
    @NotBlank(message = "must not be blank")
    private String city;
    @NotBlank(message = "must not be blank")
    private String state;
    @NotBlank(message = "must not be blank")
    private String postalCode;
    @NotNull(message = "must not be null")
    private Long customerId;
}
