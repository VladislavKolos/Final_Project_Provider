package org.example.service;

import org.example.model.EmailToken;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.User;
import org.example.repository.EmailTokenRepository;
import org.example.service.impl.EmailTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailTokenServiceTest {

    @InjectMocks
    private EmailTokenServiceImpl tokenService;

    @Mock
    private EmailTokenRepository emailTokenRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private static PasswordEncoder passwordEncoder;

    private static final User user = createUser();

    @Test
    public void testSendConfirmationEmailSuccess() {
        String newEmail = "vladislav.kolos.y@gmail.com";
        String newUsername = "UserVladislav";
        String newPhone = "+375298879322";

        EmailToken emailToken = EmailToken.builder()
                .id(5)
                .token(UUID.randomUUID().toString())
                .email(newEmail)
                .username(newUsername)
                .phone(newPhone)
                .expiryDate(LocalDateTime.now())
                .user(user)
                .build();

        when(emailTokenRepository.save(any(EmailToken.class))).thenReturn(emailToken);

        tokenService.sendConfirmationEmail(user, newEmail, newUsername, newPhone);

        ArgumentCaptor<SimpleMailMessage> argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(argumentCaptor.capture());
    }

    private static User createUser() {
        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_CLIENT");

        Status status = new Status();
        status.setId(1);
        status.setName("active");

        return User.builder()
                .id(5)
                .username("VladislavKolos")
                .password(passwordEncoder != null ? passwordEncoder.encode("Vk704101") : null)
                .email("vlad@gmail.com")
                .phone("+375335678934")
                .role(role)
                .status(status)
                .build();
    }
}
