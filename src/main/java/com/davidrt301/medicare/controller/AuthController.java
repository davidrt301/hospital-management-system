package com.davidrt301.medicare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidrt301.medicare.dto.request.AuthLoginRequest;
import com.davidrt301.medicare.dto.request.AuthRegisterRequest;
import com.davidrt301.medicare.dto.response.AuthResponse;
import com.davidrt301.medicare.dto.response.AuthResponseDto;
import com.davidrt301.medicare.dto.response.MessageResponse;
import com.davidrt301.medicare.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Request Inválido")
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody AuthRegisterRequest request) {
        log.info("Solicitud para registrar nuevo usuario recibida: {}", request.nameUser());
        try {
            MessageResponse response = authService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            log.warn("Error al registrar usuario: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        log.info("Solicitud para iniciar sesión recibida para usuario: {}", request.username());
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            log.warn("Error en el login: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new AuthResponse(ex.getMessage()));
        }
    }

}
