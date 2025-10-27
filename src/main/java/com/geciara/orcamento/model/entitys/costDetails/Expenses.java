package com.geciara.orcamento.model.entitys.costDetails;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Expenses {
    private BigDecimal marketplace;
    private BigDecimal commission;
    private BigDecimal card;
}
