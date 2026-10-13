package com.emanuele.ecommerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
    private Long id;
    private String street;
    private Integer number;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
    private Long customerId;
}
