package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.AuthenticationRequestDTO;
import org.example.dto.requestdto.RegisterRequestDTO;
import org.example.dto.responsedto.AuthenticationResponseDTO;
import org.example.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        AuthenticationResponseDTO authResponseDTO = service.register(request);

        log.info("User successfully registered");

        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        AuthenticationResponseDTO authResponseDTO = service.authenticate(request);

        log.info("User successfully authenticated");

        return ResponseEntity.ok(authResponseDTO);
    }

}
