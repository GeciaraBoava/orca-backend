package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.UserRequestDTO;
import com.geciara.orcamento.dto.UserResponseDTO;
import com.geciara.orcamento.dto.UserUpdateRequestDTO;
import com.geciara.orcamento.model.entitys.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserRequestDTO dto) {

        if (dto == null) return null;

        User user = new User();

        user.getRegister().setName(dto.getName());
        user.setUsername(dto.getLogin());
        user.getRegister().setPhone(dto.getPhone());
        user.getRegister().setEmail(dto.getEmail());
        user.getRegister().setAddress(dto.getAddress());
        user.getRegister().setCity(dto.getCity());
        user.getRegister().setUf(dto.getUf());
        user.setRole(dto.getRole());
        user.setRegisteredAt(LocalDateTime.now());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("123456")); //senha padrão a ser alterada pelo 'user'

        return user;
    }

    public User updateFromDTO(UserUpdateRequestDTO dto,
                              User user) {

        if (dto == null) return null;

        if(dto.getName() != null) user.getRegister().setName(dto.getName());
        if(dto.getLogin() != null) user.setUsername(dto.getLogin());
        if(dto.getPassword() != null) user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if(dto.getPhone() != null) user.getRegister().setPhone(dto.getPhone());
        if(dto.getEmail() != null) user.getRegister().setEmail(dto.getEmail());
        if(dto.getAddress() != null) user.getRegister().setAddress(dto.getAddress());
        if(dto.getCity() != null) user.getRegister().setCity(dto.getCity());
        if(dto.getUf() != null) user.getRegister().setUf(dto.getUf());
        if(dto.getRole() != null) user.setRole(dto.getRole());
        if(dto.getActive() != null) user.setActive(dto.getActive());
        user.setUpdatedAt(LocalDateTime.now());

        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getRegister().getName());
        dto.setLogin(user.getUsername());
        dto.setPhone(user.getRegister().getPhone());
        dto.setEmail(user.getRegister().getEmail());
        dto.setAddress(user.getRegister().getAddress());
        dto.setCity(user.getRegister().getCity());
        dto.setUf(user.getRegister().getUf());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        dto.setRegisteredAt(user.getRegisteredAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

}