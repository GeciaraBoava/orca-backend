package com.geciara.orcamento.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class MaterialCompositionRequestDTO {

    @NotNull(message = "O ID do material é obrigatório.")
    private Long materialId;

    @NotNull(message = "A quantidade é obrigatória.")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero.")
    private BigDecimal quantity;
}


