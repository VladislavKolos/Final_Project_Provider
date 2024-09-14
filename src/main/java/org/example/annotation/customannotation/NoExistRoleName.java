package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.NoExistRoleNameValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoExistRoleNameValidator.class)
@Documented
public @interface NoExistRoleName {
    String message() default "A role with the same name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
