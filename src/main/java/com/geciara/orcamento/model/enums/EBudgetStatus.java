package com.geciara.orcamento.model.enums;

public enum EBudgetStatus {
    SKETCH("Esboço"),
    NEGOTIATION("Em negociação"),
    APROVED("Aprovado"),
    DONE("Executado"),
    LOST("Perdido");

    private String description;

    EBudgetStatus(String description) {
        this.description = description;
    }

}
