package com.geciara.orcamento.dto;

import com.geciara.orcamento.model.entitys.costDetails.Taxes;
import com.geciara.orcamento.model.enums.EBudgetStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BudgetUpdateDTO {

    private LocalDate date;
    private LocalDate dateReference;
    private Taxes taxes;
    private EBudgetStatus status;
    private Boolean active;
    private List<Long> productIds;
}
