package com.geciara.orcamento.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ECustomerType {
    PUBLIC("Público"),
    CORPORATIVE("Corporativo"),
    PRIVATE("Particular");

    private final String name;

}
