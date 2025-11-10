package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.UserRequestDTO;
import com.geciara.orcamento.dto.UserResponseDTO;
import com.geciara.orcamento.dto.UserUpdateRequestDTO;
import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.model.entitys.registerDetails.Register;
import com.geciara.orcamento.model.enums.EUserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setRole(EUserRole.valueOf(dto.getRole()));

        Register register = new Register();
        register.setName(dto.getName());
        register.setPhone(dto.getPhone());
        register.setEmail(dto.getEmail());
        register.setAddress(dto.getAddress());
        register.setCity(dto.getCity());
        register.setUf(dto.getUf());
        user.setRegister(register);

        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());

        if (user.getRegister() != null) {
            dto.setName(user.getRegister().getName());
            dto.setPhone(user.getRegister().getPhone());
            dto.setEmail(user.getRegister().getEmail());
            dto.setAddress(user.getRegister().getAddress());
            dto.setCity(user.getRegister().getCity());
            dto.setUf(user.getRegister().getUf());
        }

        dto.setActive(user.isActive());
        dto.setRegisteredAt(user.getRegisteredAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public User updateFromDTO(UserUpdateRequestDTO dto, User user) {
        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getRole() != null) user.setRole(EUserRole.valueOf(dto.getRole()));

        Register register = user.getRegister();
        if (register == null) {
            register = new Register();
            user.setRegister(register);
        }

        if (dto.getName() != null) register.setName(dto.getName());
        if (dto.getPhone() != null) register.setPhone(dto.getPhone());
        if (dto.getEmail() != null) register.setEmail(dto.getEmail());
        if (dto.getAddress() != null) register.setAddress(dto.getAddress());
        if (dto.getCity() != null) register.setCity(dto.getCity());
        if (dto.getUf() != null) register.setUf(dto.getUf());

        return user;
    }
}
