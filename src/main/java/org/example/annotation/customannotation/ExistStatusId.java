package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ExistStatusIdValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistStatusIdValidator.class)
@Documented
public @interface ExistStatusId {
    String message() default "Invalid Status ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
