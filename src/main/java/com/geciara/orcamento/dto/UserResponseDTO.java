package com.geciara.orcamento.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private Long id;
    private String role;

    // Campos do EntityDates
    private String name;
    private String cnpjCpf;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String uf;
    private boolean active;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
