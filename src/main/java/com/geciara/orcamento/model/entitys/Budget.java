package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.entitys.costDetails.*;
import com.geciara.orcamento.model.enums.EBudgetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
    @SequenceGenerator(name = "supplier_seq", sequenceName = "supplier_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    private LocalDate dateReference;

    @Embedded
    private Customer customer;

    @Column(nullable = false)
    private List<Product> product;

    @Embedded
    private Taxes taxes;

    @Embedded
    private Freight freight;

    @Embedded
    private Installation installation;

    @Embedded
    private Management management;

    @Column(nullable = false)
    private BigDecimal totalCost;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private FixedExpensesBDI FixedExpenses;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private EBudgetStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        registeredAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public BigDecimal getCost(LocalDate baseDate) {
        return product.stream()
                .map(mc -> Optional.ofNullable(mc.getCost(baseDate)).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
