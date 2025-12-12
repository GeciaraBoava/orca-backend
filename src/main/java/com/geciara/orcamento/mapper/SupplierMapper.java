package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.SupplierRequestDTO;
import com.geciara.orcamento.dto.SupplierResponseDTO;
import com.geciara.orcamento.dto.SupplierUpdateDTO;
import com.geciara.orcamento.model.entitys.Supplier;
import com.geciara.orcamento.model.entitys.registerDetails.EntityDates;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SupplierMapper {
    public Supplier toEntity(SupplierRequestDTO dto) {

        if(dto == null) return null;

        Supplier supplier = new Supplier();
        EntityDates entityDates = new EntityDates();
        supplier.setEntityDates(entityDates);

        supplier.getEntityDates().setName(dto.getName());
        supplier.getEntityDates().setCnpjCpf(dto.getCnpjCpf());
        supplier.getEntityDates().setPhone(dto.getPhoneNumber());
        supplier.setContactName(dto.getContactName());
        supplier.getEntityDates().setEmail(dto.getEmail());
        supplier.getEntityDates().setAddress(dto.getAddress());
        supplier.getEntityDates().setCity(dto.getCity());
        supplier.getEntityDates().setUf(dto.getUf());
        supplier.setRegisteredAt(LocalDateTime.now());
        supplier.setActive(true);

        return supplier;
    }

    public Supplier updateFromDTO(SupplierUpdateDTO dto,
                                  Supplier supplier) {

        if(dto == null) return null;

        if(dto.getName() != null) supplier.getEntityDates().setName(dto.getName());
        if(dto.getCnpjCpf() != null) supplier.getEntityDates().setCnpjCpf(dto.getCnpjCpf());
        if(dto.getPhoneNumber() != null) supplier.getEntityDates().setPhone(dto.getPhoneNumber());
        if(dto.getContactName() != null) supplier.setContactName(dto.getContactName());
        if(dto.getEmail() != null) supplier.getEntityDates().setEmail(dto.getEmail());
        if(dto.getAddress() != null) supplier.getEntityDates().setAddress(dto.getAddress());
        if(dto.getCity() != null) supplier.getEntityDates().setCity(dto.getCity());
        if(dto.getUf() != null) supplier.getEntityDates().setUf(dto.getUf());
        if(dto.getActive() != null) supplier.setActive(dto.getActive());
        supplier.setUpdatedAt(LocalDateTime.now());

        return supplier;
    }

    public SupplierResponseDTO toResponseDTO(Supplier supplier) {
        SupplierResponseDTO dto = new SupplierResponseDTO();

        dto.setId(supplier.getId());
        dto.setName(supplier.getEntityDates().getName());
        dto.setCnpjCpf(supplier.getEntityDates().getCnpjCpf());
        dto.setPhoneNumber(supplier.getEntityDates().getPhone());
        dto.setContactName(supplier.getContactName());
        dto.setEmail(supplier.getEntityDates().getEmail());
        dto.setAddress(supplier.getEntityDates().getAddress());
        dto.setCity(supplier.getEntityDates().getCity());
        dto.setUf(supplier.getEntityDates().getUf());
        dto.setActive(supplier.isActive());
        dto.setRegisteredAt(supplier.getRegisteredAt());
        dto.setUpdatedAt(supplier.getUpdatedAt());

        return dto;
    }

}