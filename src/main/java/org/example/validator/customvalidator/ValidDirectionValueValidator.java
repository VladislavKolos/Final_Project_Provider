package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ValidDirectionValue;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidDirectionValueValidator implements ConstraintValidator<ValidDirectionValue, String> {
    @Override
    public boolean isValid(String direction, ConstraintValidatorContext context) {
        if (direction != null && !direction.equals("asc")) {
            log.info("Invalid direction value: " + direction);
            return false;
        }
        return true;
    }
}
