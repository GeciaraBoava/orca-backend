package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterialUpdateDTO {

    @NotNull
    private Long id;

    private String description;
    private String materialTypeDescription;
    private String unitMeasureDescription;
    private BigDecimal currentPrice;
    private Boolean active;

}