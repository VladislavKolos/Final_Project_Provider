package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.NoExistPlanName;
import org.example.repository.PlanRepository;
import org.springframework.stereotype.Component;

/**
 * No plan validator with the same name.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoExistPlanNameValidator implements ConstraintValidator<NoExistPlanName, String> {

    private final PlanRepository planRepository;

    @Override
    public boolean isValid(String planName, ConstraintValidatorContext context) {
        if (planName != null && planRepository.existsByName(planName)) {
            log.info("A plan with the same name: " + planName + " already exists");
            return false;
        }
        return true;
    }
}
