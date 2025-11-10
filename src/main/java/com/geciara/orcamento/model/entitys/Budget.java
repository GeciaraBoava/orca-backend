package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.entitys.costDetails.Taxes;
import com.geciara.orcamento.model.enums.EBudgetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_seq")
    @SequenceGenerator(name = "budget_seq", sequenceName = "budget_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    private LocalDate dateReference;

    @Embedded
    private Customer customer;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Embedded
    private Taxes taxes = new Taxes();

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EBudgetStatus status = EBudgetStatus.SKETCH;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        registeredAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void updateProductsCostAndPrice(LocalDate baseDate) {
        for (Product product : products) {
            product.updateCostAndPrice(this, baseDate);
        }
        // Atualiza totals do budget
        this.totalCost = products.stream()
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setBudget(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setBudget(null);
    }

}
