package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.UserRequestDTO;
import com.geciara.orcamento.dto.UserResponseDTO;
import com.geciara.orcamento.dto.UserUpdateRequestDTO;
import com.geciara.orcamento.exceptions.EmailAlreadyExistsException;
import com.geciara.orcamento.exceptions.UserNotFoundException;
import com.geciara.orcamento.mapper.UserMapper;
import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.model.entitys.registerDetails.Register;
import com.geciara.orcamento.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.geciara.orcamento.model.enums.EUserRole.ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private Register register;
    private User user;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;
    private UserUpdateRequestDTO updateRequestDTO;
    private UserResponseDTO responseUpdateDTO;
    private final LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0);

    @BeforeEach
    void setUp() {
        register = new Register(
                "Geciara Boava",
                "99999 9999",
                "geciara@gmail.com",
                "Rua Fulano de Tal",
                "Curitiba",
                "PR"
        );

        user = new User(
                1L,
                "GeciaraBoava",
                "123456",
                ADMIN,
                register,
                true,
                fixedTime,
                fixedTime
        );

        requestDTO = new UserRequestDTO(
                "GeciaraBoava",
                "Geciara Boava",
                "000000009",
                "geciara@gmail.com",
                "Rua Fulano de tal",
                "Curitiba",
                "PR",
                ADMIN
        );

        responseDTO = new UserResponseDTO(
                1L,
                "Geciara Boava",
                "GeciaraBoava",
                "000000009",
                "geciara@gmail.com",
                "Rua Fulano de Tal",
                "Curitiba",
                "PR",
                ADMIN,
                true,
                fixedTime,
                fixedTime
        );

        updateRequestDTO = new UserUpdateRequestDTO(
                "Geciara Cardoso Boava",
                "GeciaraBoava",
                "123456",
                "000000009",
                "geciaracardoso@gmail.com",
                "Rua Ciclano de Tal",
                "Curitiba",
                "PR",
                ADMIN,
                true
        );

        responseUpdateDTO = new UserResponseDTO(
                1L,
                "Geciara Cardoso Boava",
                "GeciaraBoava",
                "000000009",
                "geciaracardoso@gmail.com",
                "Rua Ciclano de Tal",
                "Curitiba",
                "PR",
                ADMIN,
                true,
                fixedTime,
                fixedTime
        );
    }


    @Test
    @DisplayName("Deve salvar um usuário com sucesso quando os dados são válidos e o email não existe")
    void shouldSaveUserSuccessfully() {

        when(userMapper.toEntity(requestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.save(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getName(), result.getName());
        assertEquals(responseDTO.getLogin(), result.getLogin());
        assertEquals(responseDTO.getPhone(), result.getPhone());
        assertEquals(responseDTO.getEmail(), result.getEmail());
        assertEquals(responseDTO.getAddress(), result.getAddress());
        assertEquals(responseDTO.getCity(), result.getCity());
        assertEquals(responseDTO.getUf(), result.getUf());
        assertEquals(responseDTO.getRole(), result.getRole());
        assertEquals(responseDTO.isActive(), result.isActive());
        assertEquals(responseDTO.getRegisteredAt(), result.getRegisteredAt());
        assertEquals(responseDTO.getUpdatedAt(), result.getUpdatedAt());

        verify(userRepository, times(1)).existsByCadastroEmail(requestDTO.getEmail());
        verify(userMapper, times(1)).toEntity(requestDTO);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponseDTO(user);
        verifyNoMoreInteractions(userRepository, userMapper);

    }

    @Test
    @DisplayName("Deve atualizar um usuário com sucesso quando o ID é válido e os dados são consistentes")
    void shouldUpdateUserSuccessfully() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User updatedUserEntity = new User(
                user.getId(),
                updateRequestDTO.getLogin(),
                updateRequestDTO.getPassword(),
                updateRequestDTO.getRole(),
                new Register(
                        updateRequestDTO.getName(),
                        updateRequestDTO.getPhone(),
                        updateRequestDTO.getEmail(),
                        updateRequestDTO.getAddress(),
                        updateRequestDTO.getCity(),
                        updateRequestDTO.getUf()
                ),
                updateRequestDTO.getActive(),
                user.getRegisteredAt(),
                fixedTime
        );

        when(userMapper.updateFromDTO(updateRequestDTO, user)).thenReturn(updatedUserEntity);
        when(userRepository.save(updatedUserEntity)).thenReturn(updatedUserEntity);
        when(userMapper.toResponseDTO(updatedUserEntity)).thenReturn(responseUpdateDTO);

        UserResponseDTO result = userService.update(user.getId(), updateRequestDTO);

        // Asserções para verificar o resultado
        assertNotNull(result);
        assertEquals(responseUpdateDTO.getId(), result.getId());
        assertEquals(responseUpdateDTO.getName(), result.getName());
        assertEquals(responseUpdateDTO.getLogin(), result.getLogin());
        assertEquals(responseUpdateDTO.getPhone(), result.getPhone());
        assertEquals(responseUpdateDTO.getEmail(), result.getEmail());
        assertEquals(responseUpdateDTO.getAddress(), result.getAddress()); // Agora esperando "Rua Ciclano de Tal"
        assertEquals(responseUpdateDTO.getCity(), result.getCity());
        assertEquals(responseUpdateDTO.getUf(), result.getUf());
        assertEquals(responseUpdateDTO.getRole(), result.getRole());
        assertEquals(responseUpdateDTO.isActive(), result.isActive());
        assertEquals(responseUpdateDTO.getRegisteredAt(), result.getRegisteredAt());
        assertEquals(responseUpdateDTO.getUpdatedAt(), result.getUpdatedAt());


        // Verificações de interações com os mocks
        verify(userRepository, times(1)).findById(user.getId());
        verify(userMapper, times(1)).updateFromDTO(updateRequestDTO, user);
        verify(userRepository, times(1)).save(updatedUserEntity);
        verify(userMapper, times(1)).toResponseDTO(updatedUserEntity);
        verifyNoMoreInteractions(userRepository, userMapper);
    }


    @Test
    @DisplayName("Deve lançar EmailAlreadyExistsException ao tentar salvar com um email já existente")
    void shouldThrowExceptionWhenEmailAlreadyExistsOnSave() {

        when(userRepository.existsByCadastroEmail(requestDTO.getEmail())).thenReturn(true);


        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.save(requestDTO)
        );

        assertEquals("Email já está em uso.", exception.getMessage());

        verify(userRepository, times(1)).existsByCadastroEmail(requestDTO.getEmail());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toEntity(any());
        verify(userMapper, never()).toResponseDTO(any());
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException ao tentar atualizar um usuário que não existe")
    void shouldThrowExceptionWhenUserNotFoundOnUpdate() {
        Long invalidId = 99L; // ID que não existe

        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.update(invalidId, updateRequestDTO)
        );

        assertEquals("Usuário não encontrado com o id fornecido", exception.getMessage());

        verify(userRepository, times(1)).findById(invalidId);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).updateFromDTO(any(), any());
        verify(userMapper, never()).toResponseDTO(any());
        verifyNoMoreInteractions(userRepository, userMapper);
    }

}
