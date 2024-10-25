package org.example.annotation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validator.customvalidator.UniqueSubscriptionValidator;

import java.lang.annotation.*;

/**
 * Annotation for checking the uniqueness of a subscription.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueSubscriptionValidator.class)
@Documented
public @interface UniqueSubscription {
    String message() default "User is already subscribed to this plan";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
