package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistStatusId;
import org.example.repository.StatusRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExistStatusIdValidator implements ConstraintValidator<ExistStatusId, Integer> {

    private final StatusRepository statusRepository;

    @Override
    public boolean isValid(Integer statusId, ConstraintValidatorContext context) {
        if (statusId != null && !statusRepository.existsById(statusId)) {
            log.info("Status ID: " + statusId + " not found");
            return false;
        }
        return true;
    }
}
