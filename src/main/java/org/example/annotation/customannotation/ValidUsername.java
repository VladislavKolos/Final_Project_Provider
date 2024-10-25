package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ExistUsernameValidator;

import java.lang.annotation.*;

/**
 * Annotation for checking the validity of Username.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistUsernameValidator.class)
@Documented
public @interface ValidUsername {
    String message() default "Invalid Username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
