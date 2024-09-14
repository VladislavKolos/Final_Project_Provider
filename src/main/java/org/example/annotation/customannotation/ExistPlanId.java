package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ExistPlanIdValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistPlanIdValidator.class)
@Documented
public @interface ExistPlanId {
    String message() default "Invalid Plan ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
