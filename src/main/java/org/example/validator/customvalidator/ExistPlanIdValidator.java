package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistPlanId;
import org.example.repository.PlanRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExistPlanIdValidator implements ConstraintValidator<ExistPlanId, Integer> {

    private final PlanRepository planRepository;

    @Override
    public boolean isValid(Integer planId, ConstraintValidatorContext context) {
        if (planId != null && !planRepository.existsById(planId)) {
            log.info("Plan ID: " + planId + " not found");
            return false;
        }
        return true;
    }
}
