package com.geciara.orcamento.model.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum EUserRole {
    ADMIN("ROLE_ADMIN", "Acesso completo ao sistema"),
    MANAGER("ROLE_MANAGER", "Pode gerenciar orçamentos e usuários"),
    BUDGET("ROLE_BUDGET", "Pode cadastrar e editar insumos e orçamentos"),
    COMMERCIAL("ROLE_COMMERCIAL", "Pode consultar e imprimir orçamentos");

    private final String role;
    private final String description;

    EUserRole(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public GrantedAuthority getAuthority() {
        return new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + this.name()); //name retorna o nome da role, ex.: ADMIN
    }
}

