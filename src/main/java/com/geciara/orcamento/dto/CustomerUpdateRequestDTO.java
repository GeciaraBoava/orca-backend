package com.geciara.orcamento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerUpdateRequestDTO {

    @NotNull
    private Long id;

    private String name;
    private String cnpjCpf;
    private String phoneNumber;
    private String contactName;

    @Email(message = "Email inválido")
    private String email;

    private String address;
    private String city;

    @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
    private String uf;

    private String customerType;
    private Boolean active;
}