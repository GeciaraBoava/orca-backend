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

    public BigDecimal calculateTotal(BigDecimal base) {
        BigDecimal total = BigDecimal.ZERO;
        if (base == null) return total;

        total = total.add(icms != null ? base.multiply(icms.divide(BigDecimal.valueOf(100))) : BigDecimal.ZERO);
        total = total.add(iss != null ? base.multiply(iss.divide(BigDecimal.valueOf(100))) : BigDecimal.ZERO);
        total = total.add(pis != null ? base.multiply(pis.divide(BigDecimal.valueOf(100))) : BigDecimal.ZERO);
        total = total.add(cofins != null ? base.multiply(cofins.divide(BigDecimal.valueOf(100))) : BigDecimal.ZERO);

        return total;
    }

    public Taxes(Taxes taxes) {
        this.icms = taxes.icms;
        this.iss = taxes.iss;
        this.pis = taxes.pis;
        this.cofins = taxes.cofins;
    }
}

