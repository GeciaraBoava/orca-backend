package com.geciara.orcamento.model.entitys.costDetails;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlyFixedExpense {

    private String description;
    private BigDecimal value;
}
