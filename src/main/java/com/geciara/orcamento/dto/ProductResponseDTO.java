package com.geciara.orcamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private Long budgetId;
    private String description;
    private BigDecimal cost;
    private BigDecimal price;
}
