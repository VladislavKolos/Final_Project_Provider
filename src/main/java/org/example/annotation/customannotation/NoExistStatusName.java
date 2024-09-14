package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.NoExistStatusNameValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoExistStatusNameValidator.class)
@Documented
public @interface NoExistStatusName {
    String message() default "A status with the same name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
