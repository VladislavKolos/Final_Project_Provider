package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ValidUsername;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Username validator for authentication.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExistUsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (!userRepository.existsByUsername(username)) {
            log.info("Invalid Username: " + username);
            return false;
        }
        return true;
    }
}
