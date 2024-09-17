package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ProviderNotFoundException;
import org.example.exception.ProviderTokenException;
import org.example.model.EmailToken;
import org.example.model.User;
import org.example.repository.EmailTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final EmailTokenRepository emailTokenRepository;

    private final UserService userService;

    @Value("${app.email.confirmation.url}")
    private String confirmationUrl;

    @Transactional
    public void sendConfirmationEmail(User user, String newEmail) {
        String token = UUID.randomUUID().toString();
        EmailToken emailToken = new EmailToken();
        emailToken.setToken(token);
        emailToken.setUser(user);
        emailToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        emailTokenRepository.save(emailToken);

        String link = confirmationUrl + "?token=" + token;
        String message = "Confirm your new email by following the link: " + link;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(newEmail);
        simpleMailMessage.setSubject("Confirmation of email change");
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);

    }

    @Transactional
    public void confirmEmail(String token) {
        EmailToken emailToken = emailTokenRepository.findByToken(token)
                .orElseThrow(() -> new ProviderTokenException("Invalid token"));

        if (emailToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ProviderTokenException("Token expired");
        }

        User user = userService.findUserByEmail(emailToken.getUser().getEmail())
                .orElseThrow(() -> new ProviderNotFoundException("User not found"));

        user.setEmail(emailToken.getUser().getEmail());
        userService.save(user);
    }
}
