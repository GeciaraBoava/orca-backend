package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.enums.ETypeMaterialComposition;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_composition")
public class ItemComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_composition_seq")
    @SequenceGenerator(name = "item_composition_seq", sequenceName = "item_composition_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ETypeMaterialComposition type;

    @ManyToOne
    @JoinColumn(name = "composition_id")
    private Composition composition;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal quantity;

    private BigDecimal cost;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreatev() {
        registeredAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Cálculo de custo baseado em data-base
    public BigDecimal getCost(LocalDate baseDate) {
        if (type == ETypeMaterialComposition.COMPOSITION && composition != null) {
            return composition.getCost(baseDate).multiply(quantity);
        } else if (type == ETypeMaterialComposition.MATERIAL && material != null) {
            return material.getPriceByBaseDate(baseDate).multiply(quantity);
        }
        return BigDecimal.ZERO;
    }

    // Valida se todos os valores são positivos
    public void validatePositive(BigDecimal... values) {
        for (BigDecimal val : values) {
            if (val == null || val.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Valores devem ser maiores que zero.");
            }
        }
    }

    // Exemplo de cálculo de peso por material
    public void setQuantityAsWeightByMaterial(BigDecimal width, BigDecimal height, BigDecimal depthMillimeter, BigDecimal specificWeightKgM3) {
        validatePositive(width, height, depthMillimeter, specificWeightKgM3);
        // Considerando depth em mm, convertemos para metros
        BigDecimal depthMeters = depthMillimeter.divide(BigDecimal.valueOf(1000));
        this.quantity = width.multiply(height).multiply(depthMeters).multiply(specificWeightKgM3);
    }
}
