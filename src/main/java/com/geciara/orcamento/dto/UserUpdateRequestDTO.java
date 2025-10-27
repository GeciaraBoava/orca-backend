package com.geciara.orcamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geciara.orcamento.model.enums.EUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {

    @NotNull
    private Long id;

    private String name;

    @JsonIgnore
    private String login;

    @JsonIgnore
    private String password;

    private String phone;

    @Email(message = "Email inválido")
    private String email;

    private String address;
    private String city;

    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String uf;

    private EUserRole role;
    private Boolean active;

}