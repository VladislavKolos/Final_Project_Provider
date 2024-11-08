package org.example.service;

import org.example.model.EmailToken;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.User;
import org.example.repository.EmailTokenRepository;
import org.example.service.impl.EmailTokenServiceImpl;
import org.example.util.ProviderConstantUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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

    private static final User user = createUser();

    @Test
    public void testSendConfirmationEmailSuccess() {
        String newEmail = "vladislav.kolos.y@gmail.com";
        String newUsername = "UserVladislav";
        String newPhone = "+375298879322";

        EmailToken emailToken = EmailToken.builder()
                .token(UUID.randomUUID().toString())
                .email(newEmail)
                .username(newUsername)
                .phone(newPhone)
                .expiryDate(LocalDateTime.now().plusMinutes(ProviderConstantUtil.ADDITIONAL_MINUTES))
                .user(user)
                .build();

        ArgumentCaptor<EmailToken> tokenArgumentCaptor = ArgumentCaptor.forClass(EmailToken.class);

        when(emailTokenRepository.save(tokenArgumentCaptor.capture())).thenReturn(emailToken);

        tokenService.sendConfirmationEmail(user, newEmail, newUsername, newPhone);

        verify(emailTokenRepository).save(tokenArgumentCaptor.capture());

        ArgumentCaptor<SimpleMailMessage> argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(argumentCaptor.capture());

        SimpleMailMessage simpleMailMessage = argumentCaptor.getValue();
        EmailToken expectedEmailToken = tokenArgumentCaptor.getValue();

        assertNotNull(simpleMailMessage);
        assertNotNull(expectedEmailToken);
    }

    private static User createUser() {
        Role role = new Role();
        role.setName("ROLE_CLIENT");

        Status status = new Status();
        status.setName("active");

        return User.builder()
                .username("VladislavKolos")
                .password("Vk704101")
                .email("vlad@gmail.com")
                .phone("+375335678934")
                .role(role)
                .status(status)
                .build();
    }
}
