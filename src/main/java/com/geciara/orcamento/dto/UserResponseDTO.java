package com.geciara.orcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geciara.orcamento.model.enums.EUserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private Long id;
    private EUserRole role;

    // Campos do PersonDates
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String uf;

    private boolean active;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
