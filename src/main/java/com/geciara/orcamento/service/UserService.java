package com.geciara.orcamento.service;

import com.geciara.orcamento.config.security.TokenService;
import com.geciara.orcamento.dto.UserRequestDTO;
import com.geciara.orcamento.dto.UserResponseDTO;
import com.geciara.orcamento.dto.UserUpdateRequestDTO;
import com.geciara.orcamento.exceptions.EmailAlreadyExistsException;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.exceptions.UserNotFoundException;
import com.geciara.orcamento.mapper.UserMapper;
import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       BCryptPasswordEncoder passwordEncoder,
                       TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public UserResponseDTO save(UserRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getEmail())) {
            throw new EmailAlreadyExistsException("E-mail já está em uso.");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode("123456"));

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> listAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Usuário não encontrado."));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    public UserResponseDTO update(Long id, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getUsername())) {
            if (userRepository.existsByUsername(dto.getEmail())) {
                throw new EmailAlreadyExistsException("E-mail já está em uso.");
            }
            user.setUsername(dto.getEmail());
        }

        User updatedUser = userMapper.updateFromDTO(dto, user);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            updatedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(updatedUser);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ItemNotFoundException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void updatePassword(Long id, String newPassword, String oldPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        user.setActive(true);
        userRepository.save(user);
    }
}
