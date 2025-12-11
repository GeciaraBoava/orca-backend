package com.geciara.orcamento.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {

    private String password;
    private String role;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String uf;
    private boolean active;
}
