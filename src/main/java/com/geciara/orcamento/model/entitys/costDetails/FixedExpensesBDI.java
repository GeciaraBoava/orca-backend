package com.geciara.orcamento.model.entitys.costDetails;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Embeddable
public class FixedExpensesBDI {

    private BigDecimal percentageOnBudgetCost;

    private BigDecimal percentageOnFixedExpenses;
    private List<MonthlyFixedExpense> monthlyFixedExpense;
    private BigDecimal valueTotalMonthlyFixedExpense;

    private BigDecimal profitMargin;
    private BigDecimal risk;
}
