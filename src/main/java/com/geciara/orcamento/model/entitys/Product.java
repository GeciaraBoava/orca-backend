package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
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
@Table(name = "products")
public class Product {

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

    private LocalDate date;

    @Column(name = "reference_date", nullable = false)
    private LocalDate referenceDate;

    @Column(name = "itens_orcamento_list", nullable = false)
    private List<Composition> compositionList;

    @Column(nullable = false)
    private BigDecimal cost;

    public BigDecimal getCost(LocalDate baseDate) {
        return compositionList.stream()
                .map(mc -> Optional.ofNullable(mc.getCost(baseDate)).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
