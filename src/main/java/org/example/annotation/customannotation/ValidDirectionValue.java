package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ValidDirectionValueValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDirectionValueValidator.class)
@Documented
public @interface ValidDirectionValue {
    String message() default "Invalid Direction Value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
