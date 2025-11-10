package com.geciara.orcamento.model.entitys.costDetails;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Management {

    private Boolean includeInProduct;
    private BigDecimal percentage;
    private BigDecimal valuePerDays;
    private Double days;
    private Boolean utilDay;
    private BigDecimal value;
}
