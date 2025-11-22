package com.example.authservice.api.controller;

import com.example.authservice.api.dto.LoginRequestDTO;
import com.example.authservice.api.dto.LoginResponseDTO;
import com.example.authservice.business.service.AuthService;
import com.example.authservice.business.service.UserService;
import com.example.authservice.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        log.info("Start login processing");
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if (tokenOptional.isEmpty()) {
            log.info("Unauthorized, token not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenOptional.get();
        Optional<User> userByEmail = userService.findByEmail(loginRequestDTO.getEmail());

        if (userByEmail.isPresent()) {
            String role = userByEmail.get().role();
            log.info("Successfully logged in with token: {} and role: {}", token, role);
            return ResponseEntity.ok().header(
                    "X-User-Email",
                    authService.getEmailFromToken(token)).body(new LoginResponseDTO(token, role));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = authService.getEmailFromToken(authHeader.substring(7));

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok()
                .header("X-User-Email", email)
                .build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        log.info("Logout option called! Clearing context");
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Logout Successfully!", HttpStatus.OK);
    }
}
