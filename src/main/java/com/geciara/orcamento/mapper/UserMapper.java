package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.UserRequestDTO;
import com.geciara.orcamento.dto.UserResponseDTO;
import com.geciara.orcamento.dto.UserUpdateDTO;
import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.model.entitys.registerDetails.EntityDates;
import com.geciara.orcamento.model.enums.EUserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getEmail());
        user.setRole(EUserRole.getRoleByProfile(dto.getRole()));

        EntityDates entityDates = new EntityDates();
        entityDates.setName(dto.getName());
        entityDates.setCnpjCpf(dto.getCnpjCpf());
        entityDates.setPhone(dto.getPhoneNumber());
        entityDates.setEmail(dto.getEmail());
        entityDates.setAddress(dto.getAddress());
        entityDates.setCity(dto.getCity());
        entityDates.setUf(dto.getUf());
        user.setEntityDates(entityDates);

        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setRole(user.getRole().getProfile());

        if(user.getEntityDates() != null) {
            dto.setName(user.getEntityDates().getName());
            dto.setCnpjCpf(user.getEntityDates().getCnpjCpf());
            dto.setPhoneNumber(user.getEntityDates().getPhone());
            dto.setEmail(user.getEntityDates().getEmail());
            dto.setAddress(user.getEntityDates().getAddress());
            dto.setCity(user.getEntityDates().getCity());
            dto.setUf(user.getEntityDates().getUf());
        }

        dto.setActive(user.isActive());
        dto.setRegisteredAt(user.getRegisteredAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public User updateFromDTO(UserUpdateDTO dto, User user) {
        if(dto.getRole() != null) user.setRole(EUserRole.getRoleByProfile(dto.getRole()));

        user.setActive(dto.isActive());

            EntityDates entityDates = user.getEntityDates();
        if(entityDates == null) {
            entityDates = new EntityDates();
            user.setEntityDates(entityDates);
        }

        if(dto.getName() != null) entityDates.setName(dto.getName());
        if(dto.getCnpjCpf() != null) entityDates.setCnpjCpf(dto.getCnpjCpf());
        if(dto.getPhoneNumber() != null) entityDates.setPhone(dto.getPhoneNumber());
        if(dto.getEmail() != null) {
            entityDates.setEmail(dto.getEmail());
            // Atualiza username também (já que username = email)
            user.setUsername(dto.getEmail());
        }
        if(dto.getAddress() != null) entityDates.setAddress(dto.getAddress());
        if(dto.getCity() != null) entityDates.setCity(dto.getCity());
        if(dto.getUf() != null) entityDates.setUf(dto.getUf());

        return user;
    }
}