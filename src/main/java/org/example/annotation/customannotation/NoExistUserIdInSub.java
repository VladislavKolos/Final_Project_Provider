package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.NoExistUserIdInSubValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoExistUserIdInSubValidator.class)
@Documented
public @interface NoExistUserIdInSub {
    String message() default "Invalid User ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
