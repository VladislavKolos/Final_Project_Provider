package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ProviderTokenException;
import org.example.model.EmailToken;
import org.example.model.User;
import org.example.repository.EmailTokenRepository;
import org.example.service.EmailTokenService;
import org.example.util.ProviderConstantUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for managing email confirmation tokens.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailTokenServiceImpl implements EmailTokenService {

    private final JavaMailSender javaMailSender;

    private final EmailTokenRepository emailTokenRepository;

    private final MessageSource messageSource;

    @Value("${app.email.confirmation.url}")
    private String confirmationUrl;

    @Value("${app.email}")
    private String appEmail;

    /**
     * Send a confirmation email to the user.
     *
     * @param user     The user to whom the letter should be sent.
     * @param newEmail The user's new email address.
     */
    @Override
    @Transactional
    public void sendConfirmationEmail(User user, String newEmail, String newUsername, String newPhone) {
        String token = UUID.randomUUID().toString();
        EmailToken emailToken = createEmailToken(user, token, newEmail, newUsername, newPhone);
        emailTokenRepository.save(emailToken);

        String link = confirmationUrl + "?token=" + token;
        String message = "Confirm your new email by following the link: " + link;

        SimpleMailMessage simpleMailMessage = createSimpleMailMessage(newEmail, message);
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * Find the `EmailToken` object by its token.
     *
     * @param token Search token
     * @return An `EmailToken` object if found, otherwise an exception is thrown.
     */
    @Override
    @Transactional(readOnly = true)
    public EmailToken findByToken(String token) {
        return emailTokenRepository.findByToken(token)
                .orElseThrow(() -> new ProviderTokenException(messageSource.getMessage("auth.error.invalid_token",
                        null,
                        LocaleContextHolder.getLocale())));
    }

    /**
     * Create a new EmailToken object.
     *
     * @param user  The user who owns the token.
     * @param token Email verification token.
     * @return EmailToken - confirmation token object.
     */
    private EmailToken createEmailToken(User user, String token, String email, String username, String phone) {
        EmailToken emailToken = new EmailToken();
        emailToken.setToken(token);
        emailToken.setEmail(email);
        emailToken.setUsername(username);
        emailToken.setPhone(phone);
        emailToken.setUser(user);
        emailToken.setExpiryDate(LocalDateTime.now().plusMinutes(ProviderConstantUtil.ADDITIONAL_MINUTES));

        return emailToken;
    }

    /**
     * Create a SimpleMailMessage object to send a letter.
     *
     * @param newEmail The user's new email address.
     * @param message  Text of the letter message.
     * @return SimpleMailMessage - message object to send.
     */
    private SimpleMailMessage createSimpleMailMessage(String newEmail, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(newEmail);
        simpleMailMessage.setSubject("Confirmation of email change");
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(appEmail);

        return simpleMailMessage;
    }
}
