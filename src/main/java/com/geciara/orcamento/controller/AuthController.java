package com.geciara.orcamento.controller;

import com.geciara.orcamento.config.security.TokenService;
import com.geciara.orcamento.dto.LoginRequestDTO;
import com.geciara.orcamento.dto.LoginResponseDTO;
import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.repository.UserRepository;
import com.geciara.orcamento.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Endpoints para login e registro")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(UserRepository repository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          TokenService tokenService,
                          UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Operation(summary = "Fazer login", description = "Gera um token JWT para autenticação")
    @ApiResponse(responseCode = "200", description = "Login bem-sucedido")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO body) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(body.username(), body.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken(body.username());
            var user = (User) auth.getPrincipal();
            var name = user.getPersonDates().getName();
            var role = user.getRole().getProfile();

            return ResponseEntity.ok(new LoginResponseDTO(token, name, role));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    @Operation(summary = "Fazer cadastro", description = "Faz um novo cadastro e gera um token JWT para autenticação")
//    @ApiResponse(responseCode = "200", description = "Cadastro bem-sucedido")
//    @ApiResponse(responseCode = "400", description = "Usuário já existe")
//    @PostMapping("/register")
//    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid UserRequestDTO body) {
//        try {
//            var token = userService.registerAndGenerateToken(body);
//            var name = body.getName();
//            var role = body.getRole();
//
//            return ResponseEntity.ok(new LoginResponseDTO(token, name, role));
//        } catch (EmailAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
}
