package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.model.entitys.registerDetails.PriceHistory;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import jakarta.persistence.*;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "material")
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

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "material_id")
    private Set<PriceHistory> priceHistories = new HashSet<>();

    @Column(nullable = false)
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        updatedAt = registeredAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public BigDecimal getPriceByBaseDate(LocalDate baseDate) {
        LocalDateTime baseDateTime = baseDate.atTime(LocalTime.MAX);

        // Preço mais recente até a data-base
        Optional<PriceHistory> priceUpToBaseDate = priceHistories.stream()
                .filter(ph -> !ph.getRegisteredAt().isAfter(baseDateTime))
                .max(Comparator.comparing(PriceHistory::getRegisteredAt));

        if (priceUpToBaseDate.isPresent()) {
            return priceUpToBaseDate.get().getPrice();
        }

        // Caso não haja preço até a data-base, pega o mais próximo no futuro
        return priceHistories.stream()
                .min(Comparator.comparingLong(
                        ph -> ChronoUnit.DAYS.between(baseDateTime.toLocalDate(), ph.getRegisteredAt().toLocalDate())))
                .map(PriceHistory::getPrice)
                .orElseThrow(() -> new ItemNotFoundException("Nenhum preço cadastrado para o material"));
    }

    public void addPriceHistory(PriceHistory priceHistory) {
        this.priceHistories.add(priceHistory);
    }
}
