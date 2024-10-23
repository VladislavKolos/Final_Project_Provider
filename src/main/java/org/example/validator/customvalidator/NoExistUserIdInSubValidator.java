package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.NoExistUserIdInSub;
import org.example.repository.SubscriptionRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoExistUserIdInSubValidator implements ConstraintValidator<NoExistUserIdInSub, Integer> {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext context) {
        if (userId != null && subscriptionRepository.existsByUserId(userId)) {
            log.info("This User: " + userId + " is already subscribed to a tariff plan");
            return false;
        }
        return true;
    }
}
