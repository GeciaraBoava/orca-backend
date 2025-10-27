package com.geciara.orcamento.model.entitys.costDetails;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Taxes {

    private BigDecimal icms;
    private BigDecimal iss;
    private BigDecimal pis;
    private BigDecimal cofins;
}
