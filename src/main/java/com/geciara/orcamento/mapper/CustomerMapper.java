package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.CustomerRequestDTO;
import com.geciara.orcamento.dto.CustomerResponseDTO;
import com.geciara.orcamento.dto.CustomerUpdateDTO;
import com.geciara.orcamento.model.entitys.Customer;
import com.geciara.orcamento.model.entitys.registerDetails.EntityDates;
import com.geciara.orcamento.model.enums.ECustomerType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO dto) {

        if (dto == null) return null;

        Customer customer = new Customer();
        EntityDates entityDates = new EntityDates();
        customer.setEntityDates(entityDates);

        customer.getEntityDates().setName(dto.getName());
        customer.getEntityDates().setCnpjCpf(dto.getCnpjCpf());
        customer.getEntityDates().setPhone(dto.getPhoneNumber());
        customer.setContactName(dto.getContactName());
        customer.getEntityDates().setEmail(dto.getEmail());
        customer.getEntityDates().setAddress(dto.getAddress());
        customer.getEntityDates().setCity(dto.getCity());
        customer.getEntityDates().setUf(dto.getUf());
        customer.setCustomerType(ECustomerType.fromType(dto.getCustomerType()));
        customer.setRegisteredAt(LocalDateTime.now());
        customer.setActive(true);

        return customer;
    }

    public Customer updateFromDTO(CustomerUpdateDTO dto,
                                  Customer customer) {

        if (dto == null) return null;

        if(dto.getName() != null) customer.getEntityDates().setName(dto.getName());
        if(dto.getCnpjCpf() != null) customer.getEntityDates().setCnpjCpf(dto.getCnpjCpf());
        if(dto.getPhoneNumber() != null) customer.getEntityDates().setPhone(dto.getPhoneNumber());
        if(dto.getContactName() != null) customer.setContactName(dto.getContactName());
        if(dto.getEmail() != null) customer.getEntityDates().setEmail(dto.getEmail());
        if(dto.getAddress() != null) customer.getEntityDates().setAddress(dto.getAddress());
        if(dto.getCity() != null) customer.getEntityDates().setCity(dto.getCity());
        if(dto.getUf() != null) customer.getEntityDates().setUf(dto.getUf());
        if(dto.getCustomerType() != null) customer.setCustomerType(ECustomerType.fromType(dto.getCustomerType()));
        if(dto.getActive() != null) customer.setActive(dto.getActive());
        customer.setUpdatedAt(LocalDateTime.now());

        return customer;
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getEntityDates().getName());
        dto.setCnpjCpf(customer.getEntityDates().getCnpjCpf());
        dto.setPhoneNumber(customer.getEntityDates().getPhone());
        dto.setContactName(customer.getContactName());
        dto.setEmail(customer.getEntityDates().getEmail());
        dto.setAddress(customer.getEntityDates().getAddress());
        dto.setCity(customer.getEntityDates().getCity());
        dto.setUf(customer.getEntityDates().getUf());
        dto.setCustomerType(customer.getCustomerType().getType());
        dto.setActive(customer.isActive());
        dto.setRegisteredAt(customer.getRegisteredAt());
        dto.setUpdatedAt(customer.getUpdatedAt());

        return dto;
    }

}