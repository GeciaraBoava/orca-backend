package com.geciara.orcamento.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

    private String password;
    private String role;
    private String name;
    private String cnpjCpf;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String uf;
    private boolean active;
}
