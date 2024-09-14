package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistUserId;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExistUserIdValidator implements ConstraintValidator<ExistUserId, Integer> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext context) {
        if (userId != null && !userRepository.existsById(userId)) {
            log.info("User ID: " + userId + " not found");
            return false;
        }

            return true;
    }
}
