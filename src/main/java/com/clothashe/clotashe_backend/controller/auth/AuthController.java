package com.clothashe.clotashe_backend.controller.auth;

import com.clothashe.clotashe_backend.model.dto.auth.LoginRequest;
import com.clothashe.clotashe_backend.model.dto.auth.LoginResponse;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.repository.auth.AuthInfoRepository;
import com.clothashe.clotashe_backend.security.JwtService;
import com.clothashe.clotashe_backend.service.auth.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthInfoRepository authInfoRepository;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        AuthInfoEntity user = authInfoRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        String jwtToken = authService.register(request);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
