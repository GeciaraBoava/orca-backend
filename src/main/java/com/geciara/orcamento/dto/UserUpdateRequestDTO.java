package com.geciara.orcamento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    private String role;

    private String name;
    private String phone;

    @Email(message = "Email inválido")
    private String email;

    private String address;
    private String city;
    private String uf;
}
