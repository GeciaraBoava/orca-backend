package com.geciara.orcamento.model.entitys.costDetails;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
public class Taxes {

    private BigDecimal icms = BigDecimal.ZERO;
    private BigDecimal iss = BigDecimal.ZERO;
    private BigDecimal pis = BigDecimal.ZERO;
    private BigDecimal cofins = BigDecimal.ZERO;

    public BigDecimal getTotal(BigDecimal base) {
        BigDecimal total = BigDecimal.ZERO;
        if (base == null) return total;

        total = total.add(icms != null ? taxToPercent(base, icms) : BigDecimal.ZERO);
        total = total.add(iss != null ? taxToPercent(base, iss) : BigDecimal.ZERO);
        total = total.add(pis != null ? taxToPercent(base, pis) : BigDecimal.ZERO);
        total = total.add(cofins != null ? taxToPercent(base, cofins) : BigDecimal.ZERO);

        return total;
    }

    private BigDecimal taxToPercent(BigDecimal base, BigDecimal tax) {
        if (base == null) return BigDecimal.ZERO;
        return base.multiply(tax.divide(BigDecimal.valueOf(100)));
    }

    public Taxes(Taxes taxes) {
        this.icms = taxes.icms;
        this.iss = taxes.iss;
        this.pis = taxes.pis;
        this.cofins = taxes.cofins;
    }
}

