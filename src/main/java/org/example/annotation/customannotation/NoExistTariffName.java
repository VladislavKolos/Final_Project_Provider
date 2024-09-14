package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.NoExistTariffNameValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoExistTariffNameValidator.class)
@Documented
public @interface NoExistTariffName {
    String message() default "A tariff with the same name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
