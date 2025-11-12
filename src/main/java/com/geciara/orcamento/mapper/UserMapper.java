package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.UserRequestDTO;
import com.geciara.orcamento.dto.UserResponseDTO;
import com.geciara.orcamento.dto.UserUpdateRequestDTO;
import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.model.entitys.registerDetails.PersonDates;
import com.geciara.orcamento.model.enums.EUserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getEmail());
        user.setRole(EUserRole.valueOf(dto.getRole()));

        PersonDates personDates = new PersonDates();
        personDates.setName(dto.getName());
        personDates.setPhone(dto.getPhoneNumber());
        personDates.setEmail(dto.getEmail());
        personDates.setAddress(dto.getAddress());
        personDates.setCity(dto.getCity());
        personDates.setUf(dto.getUf());
        user.setPersonDates(personDates);

        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setRole(user.getRole());

        if (user.getPersonDates() != null) {
            dto.setName(user.getPersonDates().getName());
            dto.setPhone(user.getPersonDates().getPhone());
            dto.setEmail(user.getPersonDates().getEmail());
            dto.setAddress(user.getPersonDates().getAddress());
            dto.setCity(user.getPersonDates().getCity());
            dto.setUf(user.getPersonDates().getUf());
        }

        dto.setActive(user.isActive());
        dto.setRegisteredAt(user.getRegisteredAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public User updateFromDTO(UserUpdateRequestDTO dto, User user) {
        if (dto.getRole() != null) {
            user.setRole(EUserRole.valueOf(dto.getRole()));
        }

        PersonDates personDates = user.getPersonDates();
        if (personDates == null) {
            personDates = new PersonDates();
            user.setPersonDates(personDates);
        }

        if (dto.getName() != null) personDates.setName(dto.getName());
        if (dto.getPhone() != null) personDates.setPhone(dto.getPhone());
        if (dto.getEmail() != null) {
            personDates.setEmail(dto.getEmail());
            // Atualiza username também (já que username = email)
            user.setUsername(dto.getEmail());
        }
        if (dto.getAddress() != null) personDates.setAddress(dto.getAddress());
        if (dto.getCity() != null) personDates.setCity(dto.getCity());
        if (dto.getUf() != null) personDates.setUf(dto.getUf());

        return user;
    }
}