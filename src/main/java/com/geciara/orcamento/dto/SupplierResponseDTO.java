package com.geciara.orcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geciara.orcamento.model.enums.ECustomerType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierResponseDTO {

    private Long id;
    private String name;
    private String phone;
    private String contactName;
    private String email;
    private String address;
    private String city;
    private String uf;
    private ECustomerType customerType;
    private boolean active;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}