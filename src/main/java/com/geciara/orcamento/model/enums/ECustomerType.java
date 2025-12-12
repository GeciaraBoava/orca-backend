package com.geciara.orcamento.model.enums;

import lombok.Getter;

@Getter
public enum ECustomerType {
    PUBLIC("Público"),
    CORPORATIVE("Corporativo"),
    PRIVATE("Particular");

    private final String type;

    ECustomerType(String type) {
        this.type = type;
    }

    public static ECustomerType fromType(String type) {
        for (ECustomerType e : values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }

}
