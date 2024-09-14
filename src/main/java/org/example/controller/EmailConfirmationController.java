package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm")
@RequiredArgsConstructor
public class EmailConfirmationController {

    private final EmailService emailService;

    @GetMapping("/email")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        emailService.confirmEmail(token);

        log.info("Email confirmed successfully");

        return ResponseEntity.ok("Email confirmed successfully");
    }
}
