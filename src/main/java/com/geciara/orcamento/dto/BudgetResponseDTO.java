package com.geciara.orcamento.dto;

import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.model.entitys.costDetails.Taxes;
import com.geciara.orcamento.model.enums.EBudgetStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BudgetResponseDTO {

    private Long id;
    private LocalDate date;
    private LocalDate dateReference;
    private Long customerId;
    private List<Product> products;
    private Taxes taxes;
    private BigDecimal totalCost;
    private BigDecimal totalPrice;
    private Boolean active;
    private EBudgetStatus status;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
