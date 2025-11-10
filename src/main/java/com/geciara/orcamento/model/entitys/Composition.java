package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import jakarta.persistence.*;
import lombok.*;

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
@Builder
@Entity
@Table(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "composition_seq")
    @SequenceGenerator(name = "composition_seq", sequenceName = "composition_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_type_id", nullable = false)
    private ItemType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_measure_id", nullable = false)
    private UnitMeasure unitMeasure;

    @OneToMany(mappedBy = "composition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemComposition> materialCompositions = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        updatedAt = registeredAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Calcula o custo total da composição considerando todos os ItemComposition associados,
     * usando a data-base do orçamento.
     */
    public BigDecimal getCost(LocalDate baseDate) {
        return materialCompositions.stream()
                .map(mc -> Optional.ofNullable(mc.getCost(baseDate)).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addMaterialComposition(ItemComposition mc) {
        mc.setComposition(this);
        materialCompositions.add(mc);
    }

    public void removeMaterialComposition(ItemComposition mc) {
        materialCompositions.remove(mc);
        mc.setComposition(null);
    }
}
