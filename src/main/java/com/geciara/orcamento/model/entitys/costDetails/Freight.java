package com.geciara.orcamento.model.entitys.costDetails;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Freight {

    private Boolean includeInProduct;
    private BigDecimal percentage;
    private BigDecimal valuePerKm;
    private Double km;
    private BigDecimal value;
}
