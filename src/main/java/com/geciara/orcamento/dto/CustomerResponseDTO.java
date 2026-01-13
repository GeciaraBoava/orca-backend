package com.geciara.orcamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerResponseDTO {

    private Long id;
    private String name;
    private String cnpjCpf;
    private String phoneNumber;
    private String contactName;
    private String email;
    private String address;
    private String city;
    private String uf;
    private String customerType;
    private boolean active;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}