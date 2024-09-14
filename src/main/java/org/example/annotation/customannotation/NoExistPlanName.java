package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.NoExistPlanNameValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoExistPlanNameValidator.class)
@Documented
public @interface NoExistPlanName {
    String message() default "A plan with the same name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
