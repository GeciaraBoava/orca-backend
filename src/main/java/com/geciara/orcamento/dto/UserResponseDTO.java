package com.geciara.orcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geciara.orcamento.model.enums.EUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String login;
    private EUserRole role;
    private boolean active;

    private String phone;
    private String email;
    private String address;
    private String city;
    private String uf;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}