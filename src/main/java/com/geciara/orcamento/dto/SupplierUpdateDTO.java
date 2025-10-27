package com.geciara.orcamento.dto;

import com.geciara.orcamento.model.enums.ECustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierUpdateDTO {

    @NotNull
    private Long id;

    private String name;
    private String phone;
    private String contactName;

    @Email(message = "Email inválido")
    private String email;

    private String address;
    private String city;

    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String uf;

    private ECustomerType customerType;
    private Boolean active;
}