package com.geciara.orcamento.model.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum EUserRole {
    ADMIN("ROLE_ADMIN", "Administrador", "Acesso completo ao sistema"),
    MANAGER("ROLE_MANAGER", "Gerente", "Pode gerenciar orçamentos e usuários"),
    BUDGET("ROLE_BUDGET", "Orçamentista", "Pode cadastrar e editar insumos e orçamentos"),
    COMMERCIAL("ROLE_COMMERCIAL", "Comercial", "Pode consultar e imprimir orçamentos");

    private final String role;
    private final String profile;
    private final String description;

    EUserRole(String role, String profile, String description) {
        this.role = role;
        this.profile = profile;
        this.description = description;
    }

    public GrantedAuthority getAuthority() {
        return new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + this.name());
    }

    public static EUserRole getRoleByProfile(String profile) {
        for (EUserRole role : EUserRole.values()) {
            if (role.getProfile().equalsIgnoreCase(profile)) {
                return role;
            }
        }
        return null;
    }
}

