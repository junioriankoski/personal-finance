package com.junior.personal_finance.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController {
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> registro(@Valid @RequestBody UsuarioRequest usuario) {
        String token = service.registrar(usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioRequest usuario) {
        String token = service.login(usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
