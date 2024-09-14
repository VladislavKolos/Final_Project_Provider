package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ExistStatusNameValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistStatusNameValidator.class)
@Documented
public @interface ExistStatusName {
    String message() default "Invalid Status Name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
