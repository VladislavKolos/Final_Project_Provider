package org.example.validator.uservalidator;

import lombok.RequiredArgsConstructor;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Validator of DTO objects for requests to work with users.
 * This class provides methods for checking the existence of a username, email, and phone number in the database.
 */
@Component
@RequiredArgsConstructor
public class UserRequestDTOValidator {

    private final UserRepository userRepository;

    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
