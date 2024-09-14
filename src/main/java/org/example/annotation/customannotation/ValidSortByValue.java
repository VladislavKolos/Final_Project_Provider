package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ValidSortByValueValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSortByValueValidator.class)
@Documented
public @interface ValidSortByValue {
    String message() default "Invalid Sort By Value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
