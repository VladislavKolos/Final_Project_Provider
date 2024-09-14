package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ExistRoleIdValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistRoleIdValidator.class)
@Documented
public @interface ExistRoleId {
    String message() default "Invalid Role ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
