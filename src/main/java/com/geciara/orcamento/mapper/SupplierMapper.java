package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.SupplierRequestDTO;
import com.geciara.orcamento.dto.SupplierResponseDTO;
import com.geciara.orcamento.dto.SupplierUpdateDTO;
import com.geciara.orcamento.model.entitys.Supplier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SupplierMapper {
    public Supplier toEntity(SupplierRequestDTO dto) {

        if (dto == null) return null;

        Supplier supplier = new Supplier();

        supplier.getRegister().setName(dto.getName());
        supplier.getRegister().setPhone(dto.getPhone());
        supplier.setContactName(dto.getContactName());
        supplier.getRegister().setEmail(dto.getEmail());
        supplier.getRegister().setAddress(dto.getAddress());
        supplier.getRegister().setCity(dto.getCity());
        supplier.getRegister().setUf(dto.getUf());
        supplier.setRegisteredAt(LocalDateTime.now());
        supplier.setActive(true);

        return supplier;
    }

    public Supplier updateFromDTO(SupplierUpdateDTO dto,
                                  Supplier supplier) {

        if (dto == null) return null;

        if(dto.getName() != null) supplier.getRegister().setName(dto.getName());
        if(dto.getPhone() != null) supplier.getRegister().setPhone(dto.getPhone());
        if(dto.getContactName() != null) supplier.setContactName(dto.getContactName());
        if(dto.getEmail() != null) supplier.getRegister().setEmail(dto.getEmail());
        if(dto.getAddress() != null) supplier.getRegister().setAddress(dto.getAddress());
        if(dto.getCity() != null) supplier.getRegister().setCity(dto.getCity());
        if(dto.getUf() != null) supplier.getRegister().setUf(dto.getUf());
        if(dto.getActive() != null) supplier.setActive(dto.getActive());
        supplier.setUpdatedAt(LocalDateTime.now());

        return supplier;
    }

    public SupplierResponseDTO toResponseDTO(Supplier supplier) {
        SupplierResponseDTO dto = new SupplierResponseDTO();

        dto.setId(supplier.getId());
        dto.setName(supplier.getRegister().getName());
        dto.setPhone(supplier.getRegister().getPhone());
        dto.setContactName(supplier.getContactName());
        dto.setEmail(supplier.getRegister().getEmail());
        dto.setAddress(supplier.getRegister().getAddress());
        dto.setCity(supplier.getRegister().getCity());
        dto.setUf(supplier.getRegister().getUf());
        dto.setActive(supplier.isActive());
        dto.setRegisteredAt(supplier.getRegisteredAt());
        dto.setUpdatedAt(supplier.getUpdatedAt());

        return dto;
    }

}