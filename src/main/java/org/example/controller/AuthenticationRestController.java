package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.AuthenticationRequestDTO;
import org.example.dto.requestdto.RegisterRequestDTO;
import org.example.dto.responsedto.AuthenticationResponseDTO;
import org.example.service.AuthenticationService;
import org.example.service.JwtBlacklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * A controller to handle authentication-related requests.
 * This class handles registration, authorization, and logout requests.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Operations for User registration, authentication, and logout")
public class AuthenticationRestController {

    private final AuthenticationService service;

    private final JwtBlacklistService jwtBlacklistService;

    @PostMapping("/register")
    @Operation(summary = "Register a new User", description = "This operation allows Users to register for the application")
    public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthenticationResponseDTO authResponseDTO = service.register(request);

        log.info("User successfully registered");

        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate a User", description = "This operation allows Users to authenticate with their credentials")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@Valid @RequestBody AuthenticationRequestDTO request) {
        AuthenticationResponseDTO authResponseDTO = service.authenticate(request);

        log.info("User successfully authenticated");

        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout a User", description = "This operation allows users to logout and blacklist their token")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String tokenValue = token.substring(7);

        jwtBlacklistService.addTokenToBlacklist(tokenValue);

        log.info("The User has successfully logged out. Token added to blacklist");

        return ResponseEntity.noContent().build();
    }
}
