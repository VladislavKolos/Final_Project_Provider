package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ValidSortByValue;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidSortByValueValidator implements ConstraintValidator<ValidSortByValue, String> {
    @Override
    public boolean isValid(String sortBy, ConstraintValidatorContext context) {
        if (sortBy != null && !sortBy.equals("name")) {
            log.info("Invalid sort by value: " + sortBy);
            return false;
        }
        return true;
    }
}
