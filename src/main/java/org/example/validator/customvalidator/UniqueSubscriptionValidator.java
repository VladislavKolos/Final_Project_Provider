package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.UniqueSubscription;
import org.example.repository.SubscriptionRepository;
import org.example.util.RecipientCurrentClientUtil;
import org.springframework.stereotype.Component;

/**
 * Subscription uniqueness validator.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UniqueSubscriptionValidator implements ConstraintValidator<UniqueSubscription, Integer> {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public boolean isValid(Integer planId, ConstraintValidatorContext context) {
        if (planId != null && subscriptionRepository.existsByUserIdAndPlanId(RecipientCurrentClientUtil.getCurrentClientId(),
                planId)) {
            log.info("User is already subscribed to this plan: " + planId);
            return false;
        }
        return true;
    }
}
