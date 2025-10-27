package com.geciara.orcamento.model.entitys;

import com.geciara.orcamento.model.enums.EMaterialTypeWeight;
import com.geciara.orcamento.model.enums.ETypeMaterialComposition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materials_compositions")
public class MaterialComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_composition_seq")
    @SequenceGenerator(name = "material_composition_seq", sequenceName = "material_composition_seq", allocationSize = 1)
    private Long id;

    private ETypeMaterialComposition type;

    @ManyToOne
    @JoinColumn(name = "composition_id")
    private Composition composition;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(nullable = false)
    private BigDecimal quantity;

    private BigDecimal cost;

    public void setQuantity(BigDecimal quantity) {
        validatePositive(quantity);
        this.quantity = quantity;
    }

    public void setQuantityAsArea(BigDecimal width, BigDecimal height) {
        validatePositive(width, height);
        this.quantity = width.multiply(height);
    }

    public void setQuantityAsVolumeBySides(BigDecimal width, BigDecimal height, BigDecimal depth) {
        validatePositive(width, height, depth);
        this.quantity = width.multiply(height).multiply(depth);
    }
    public void setQuantityAsVolumeByArea(BigDecimal area, BigDecimal depth) {
        validatePositive(area, depth);
        this.quantity = area.multiply(depth);
    }

    public void setQuantityAsWeightByMaterial(BigDecimal width, BigDecimal height, BigDecimal depthMillimeter, EMaterialTypeWeight materialType) {
        validatePositive(width, height, depthMillimeter, materialType.getSpecificWeightKgM3());
        BigDecimal depthInMeters = depthMillimeter.divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);
        this.quantity = width.multiply(height).multiply(depthInMeters)
                .multiply(materialType.getSpecificWeightKgM3());
    }


    public BigDecimal getCost(LocalDate baseDate) {
        BigDecimal qty = quantity != null ? quantity : BigDecimal.ONE;

        if (ETypeMaterialComposition.COMPOSITION.equals(type) && composition != null) {
            return composition.getCost(baseDate).multiply(qty);
        } else if (ETypeMaterialComposition.MATERIAL.equals(type) && material != null) {
            return material.getPrice(baseDate).multiply(qty);
        }

        return BigDecimal.ZERO;
    }


    private void validatePositive(BigDecimal... values) {
        for (BigDecimal v : values) {
            if (v == null || v.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Os valores dos dados devem ser maiores que zero.");
            }
        }
    }


}
