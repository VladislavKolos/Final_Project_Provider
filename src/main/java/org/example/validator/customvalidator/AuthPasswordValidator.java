package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ValidPassword;
import org.example.dto.requestdto.AuthenticationRequestDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Password validator for authentication
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthPasswordValidator implements ConstraintValidator<ValidPassword, AuthenticationRequestDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(AuthenticationRequestDTO request, ConstraintValidatorContext context) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.info("Invalid password");
            return false;
        }
        return true;
    }
}
