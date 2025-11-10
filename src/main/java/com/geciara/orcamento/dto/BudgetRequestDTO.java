package com.geciara.orcamento.dto;

import com.geciara.orcamento.model.entitys.costDetails.Taxes;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BudgetRequestDTO {

    @NotNull(message = "Data do orçamento é obrigatória")
    private LocalDate date;

    private LocalDate dateReference;

    @NotNull(message = "Cliente é obrigatório")
    private Long customerId;

    @NotNull(message = "Produtos são obrigatórios")
    private List<Long> productIds;

    private Taxes taxes;
}
