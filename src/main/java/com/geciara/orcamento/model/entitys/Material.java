package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.model.entitys.registerDetails.PriceHistory;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_seq")
    @SequenceGenerator(name = "material_seq", sequenceName = "material_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "material_type_id", nullable = false)
    private MaterialType materialType;

    @ManyToOne
    @JoinColumn(name = "unit_measure_id", nullable = false)
    private UnitMeasure unitMeasure;

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

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //remove preços se o material for deletado e vice-versa
    @JoinColumn(name = "material_id")
    private Set<PriceHistory> priceHistories = new HashSet<>();

    @Column(nullable = false)
    private boolean active;


    public Material(String description,
                    MaterialType materialType,
                    UnitMeasure unitMeasure,
                    BigDecimal currentPrice) {
        this.description = description;
        this.materialType = materialType;
        this.unitMeasure = unitMeasure;
        this.registeredAt = LocalDateTime.now();
        this.currentPrice = currentPrice;
    }

    //buscar preço conforme data-base do orçamento
    public BigDecimal getPrice(LocalDate baseDate) {
        LocalDateTime baseDateTime = baseDate.atTime(LocalTime.MAX);

        // 1️⃣ Busca o último preço até a data base
        Optional<PriceHistory> lastPriceBeforeBaseDate = priceHistories.stream()
                .filter(price -> !price.getRegisteredAt().isAfter(baseDateTime))
                .max(Comparator.comparing(PriceHistory::getRegisteredAt));

        if (lastPriceBeforeBaseDate.isPresent()) {
            return lastPriceBeforeBaseDate.get().getPrice();
        }

        // 2️⃣ Se não houver, busca o preço mais próximo após a data base
        return priceHistories.stream()
                .min(Comparator.comparingLong(ph ->
                        Math.abs(ChronoUnit.DAYS.between(baseDateTime.toLocalDate(), ph.getRegisteredAt().toLocalDate()))))
                .map(PriceHistory::getPrice)
                .orElseThrow(() -> new ItemNotFoundException("Nenhum preço cadastrado para o material."));
    }

}
