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

        supplier.getPersonDates().setName(dto.getName());
        supplier.getPersonDates().setPhone(dto.getPhone());
        supplier.setContactName(dto.getContactName());
        supplier.getPersonDates().setEmail(dto.getEmail());
        supplier.getPersonDates().setAddress(dto.getAddress());
        supplier.getPersonDates().setCity(dto.getCity());
        supplier.getPersonDates().setUf(dto.getUf());
        supplier.setRegisteredAt(LocalDateTime.now());
        supplier.setActive(true);

        return supplier;
    }

    public Supplier updateFromDTO(SupplierUpdateDTO dto,
                                  Supplier supplier) {

        if (dto == null) return null;

        if(dto.getName() != null) supplier.getPersonDates().setName(dto.getName());
        if(dto.getPhone() != null) supplier.getPersonDates().setPhone(dto.getPhone());
        if(dto.getContactName() != null) supplier.setContactName(dto.getContactName());
        if(dto.getEmail() != null) supplier.getPersonDates().setEmail(dto.getEmail());
        if(dto.getAddress() != null) supplier.getPersonDates().setAddress(dto.getAddress());
        if(dto.getCity() != null) supplier.getPersonDates().setCity(dto.getCity());
        if(dto.getUf() != null) supplier.getPersonDates().setUf(dto.getUf());
        if(dto.getActive() != null) supplier.setActive(dto.getActive());
        supplier.setUpdatedAt(LocalDateTime.now());

        return supplier;
    }

    public SupplierResponseDTO toResponseDTO(Supplier supplier) {
        SupplierResponseDTO dto = new SupplierResponseDTO();

        dto.setId(supplier.getId());
        dto.setName(supplier.getPersonDates().getName());
        dto.setPhone(supplier.getPersonDates().getPhone());
        dto.setContactName(supplier.getContactName());
        dto.setEmail(supplier.getPersonDates().getEmail());
        dto.setAddress(supplier.getPersonDates().getAddress());
        dto.setCity(supplier.getPersonDates().getCity());
        dto.setUf(supplier.getPersonDates().getUf());
        dto.setActive(supplier.isActive());
        dto.setRegisteredAt(supplier.getRegisteredAt());
        dto.setUpdatedAt(supplier.getUpdatedAt());

        return dto;
    }

}