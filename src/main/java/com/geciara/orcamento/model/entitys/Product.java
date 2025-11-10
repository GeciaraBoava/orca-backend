package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.entitys.costDetails.Taxes;
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
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @Column(nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemComposition> items = new ArrayList<>();

    @Embedded
    private Taxes taxes = new Taxes();

    @Column(precision = 15, scale = 4)
    private BigDecimal cost = BigDecimal.ZERO;

    @Column(precision = 15, scale = 4)
    private BigDecimal price = BigDecimal.ZERO;

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

    /**
     * Calcula o custo total baseado nas composições.
     */
    public BigDecimal calculateCost(LocalDate baseDate) {
        return items.stream()
                .map(mc -> Optional.ofNullable(mc.getCost(baseDate)).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Atualiza o custo e o preço do produto usando os impostos do budget.
     */
    public void updateCostAndPrice(Budget budget, LocalDate baseDate) {
        this.cost = calculateCost(baseDate);
        Taxes taxes = budget.getTaxes();
        this.taxes = taxes != null ? new Taxes(taxes) : new Taxes();
        this.price = this.cost.add(this.taxes.calculateTotal(this.cost));
    }
}
