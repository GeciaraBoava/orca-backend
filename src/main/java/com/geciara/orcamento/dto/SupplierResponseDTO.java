package com.geciara.orcamento.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierResponseDTO {

    private Long id;
    private String name;
    private String cnpjCpf;
    private String phoneNumber;
    private String contactName;
    private String email;
    private String address;
    private String city;
    private String uf;
    private boolean active;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}