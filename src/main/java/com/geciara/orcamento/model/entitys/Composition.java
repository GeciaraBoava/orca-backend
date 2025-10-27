package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "composition_seq")
    @SequenceGenerator(name = "composition_seq", sequenceName = "composition_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "material_type_id", nullable = false)
    private ItemType type;

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

    @OneToMany(mappedBy = "composition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialComposition> materialComposition = new ArrayList<>();

    private BigDecimal cost;

    public BigDecimal getCost(LocalDate baseDate) {
        return materialComposition.stream()
                .map(mc -> Optional.ofNullable(mc.getCost(baseDate)).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
