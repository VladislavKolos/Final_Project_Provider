package org.example.service;

import org.example.model.EmailToken;
import org.example.model.User;
import org.springframework.stereotype.Component;

/**
 * This interface defines methods for sending confirmation emails to users and retrieving
 * email tokens by their unique identifiers.
 */
@Component
public interface EmailTokenService {
    void sendConfirmationEmail(User user, String newEmail, String newUsername, String newPhone);

    EmailToken findByToken(String token);
}
