package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.CustomerRequestDTO;
import com.geciara.orcamento.dto.CustomerResponseDTO;
import com.geciara.orcamento.dto.CustomerUpdateRequestDTO;
import com.geciara.orcamento.model.entitys.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO dto) {

        if (dto == null) return null;

        Customer customer = new Customer();

        customer.getRegister().setName(dto.getName());
        customer.getRegister().setPhone(dto.getPhone());
        customer.setContactName(dto.getContactName());
        customer.getRegister().setEmail(dto.getEmail());
        customer.getRegister().setAddress(dto.getAddress());
        customer.getRegister().setCity(dto.getCity());
        customer.getRegister().setUf(dto.getUf());
        customer.setCustomerType(dto.getCustomerType());
        customer.setRegisteredAt(LocalDateTime.now());
        customer.setActive(true);

        return customer;
    }

    public Customer updateFromDTO(CustomerUpdateRequestDTO dto,
                                  Customer customer) {

        if (dto == null) return null;

        if(dto.getName() != null) customer.getRegister().setName(dto.getName());
        if(dto.getPhone() != null) customer.getRegister().setPhone(dto.getPhone());
        if(dto.getContactName() != null) customer.setContactName(dto.getContactName());
        if(dto.getEmail() != null) customer.getRegister().setEmail(dto.getEmail());
        if(dto.getAddress() != null) customer.getRegister().setAddress(dto.getAddress());
        if(dto.getCity() != null) customer.getRegister().setCity(dto.getCity());
        if(dto.getUf() != null) customer.getRegister().setUf(dto.getUf());
        if(dto.getCustomerType() != null) customer.setCustomerType(dto.getCustomerType());
        if(dto.getActive() != null) customer.setActive(dto.getActive());
        customer.setUpdatedAt(LocalDateTime.now());

        return customer;
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getRegister().getName());
        dto.setPhone(customer.getRegister().getPhone());
        dto.setContactName(customer.getContactName());
        dto.setEmail(customer.getRegister().getEmail());
        dto.setAddress(customer.getRegister().getAddress());
        dto.setCity(customer.getRegister().getCity());
        dto.setUf(customer.getRegister().getUf());
        dto.setCustomerType(customer.getCustomerType());
        dto.setActive(customer.isActive());
        dto.setRegisteredAt(customer.getRegisteredAt());
        dto.setUpdatedAt(customer.getUpdatedAt());

        return dto;
    }

}