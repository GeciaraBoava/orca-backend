package com.geciara.orcamento.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
public enum EMaterialTypeWeight {
    STEEL(BigDecimal.valueOf(7850)),
    ACRYLIC(BigDecimal.valueOf(1180)),
    ALUMINUM(BigDecimal.valueOf(2700));

    @Getter
    private BigDecimal specificWeightKgM3;
}
