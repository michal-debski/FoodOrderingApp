package com.example.authservice.api.controller;

import com.example.authservice.api.dto.LoginRequestDTO;
import com.example.authservice.api.dto.LoginResponseDTO;
import com.example.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(
          @RequestBody LoginRequestDTO loginRequestDTO
  ) {

     Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

     if(tokenOptional.isEmpty()) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
     }
     String token = tokenOptional.get();
     return ResponseEntity.ok().header("X-User-Email", authService.getEmailFromToken(token)).body(new LoginResponseDTO(token));
  }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @RequestHeader("Authorization") String authHeader
    ){
      if(authHeader == null || !authHeader.startsWith("Bearer ")) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
        String email = authService.getEmailFromToken(authHeader.substring(7));

      return authService.validateToken(authHeader.substring(7))
              ? ResponseEntity.ok()
              .header("X-User-Email", email)
              .build()
              : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
