package com.clothashe.clotashe_backend.controller.auth;

import com.clothashe.clotashe_backend.model.dto.auth.LoginRequest;
import com.clothashe.clotashe_backend.model.dto.auth.LoginResponse;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String jwtToken = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        String jwtToken = authService.register(request);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
