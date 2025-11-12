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

        customer.getPersonDates().setName(dto.getName());
        customer.getPersonDates().setPhone(dto.getPhone());
        customer.setContactName(dto.getContactName());
        customer.getPersonDates().setEmail(dto.getEmail());
        customer.getPersonDates().setAddress(dto.getAddress());
        customer.getPersonDates().setCity(dto.getCity());
        customer.getPersonDates().setUf(dto.getUf());
        customer.setCustomerType(dto.getCustomerType());
        customer.setRegisteredAt(LocalDateTime.now());
        customer.setActive(true);

        return customer;
    }

    public Customer updateFromDTO(CustomerUpdateRequestDTO dto,
                                  Customer customer) {

        if (dto == null) return null;

        if(dto.getName() != null) customer.getPersonDates().setName(dto.getName());
        if(dto.getPhone() != null) customer.getPersonDates().setPhone(dto.getPhone());
        if(dto.getContactName() != null) customer.setContactName(dto.getContactName());
        if(dto.getEmail() != null) customer.getPersonDates().setEmail(dto.getEmail());
        if(dto.getAddress() != null) customer.getPersonDates().setAddress(dto.getAddress());
        if(dto.getCity() != null) customer.getPersonDates().setCity(dto.getCity());
        if(dto.getUf() != null) customer.getPersonDates().setUf(dto.getUf());
        if(dto.getCustomerType() != null) customer.setCustomerType(dto.getCustomerType());
        if(dto.getActive() != null) customer.setActive(dto.getActive());
        customer.setUpdatedAt(LocalDateTime.now());

        return customer;
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getPersonDates().getName());
        dto.setPhone(customer.getPersonDates().getPhone());
        dto.setContactName(customer.getContactName());
        dto.setEmail(customer.getPersonDates().getEmail());
        dto.setAddress(customer.getPersonDates().getAddress());
        dto.setCity(customer.getPersonDates().getCity());
        dto.setUf(customer.getPersonDates().getUf());
        dto.setCustomerType(customer.getCustomerType());
        dto.setActive(customer.isActive());
        dto.setRegisteredAt(customer.getRegisteredAt());
        dto.setUpdatedAt(customer.getUpdatedAt());

        return dto;
    }

}