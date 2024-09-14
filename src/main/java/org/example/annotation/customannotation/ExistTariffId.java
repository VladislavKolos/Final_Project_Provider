package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.ExistTariffIdValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistTariffIdValidator.class)
@Documented
public @interface ExistTariffId {
    String message() default "Invalid Tariff ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
