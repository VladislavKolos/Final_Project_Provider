package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistStatusName;
import org.example.repository.StatusRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExistStatusNameValidator implements ConstraintValidator<ExistStatusName, String> {

    private final StatusRepository statusRepository;

    @Override
    public boolean isValid(String statusName, ConstraintValidatorContext context) {
        if (statusName != null && !statusRepository.existsByName(statusName)) {
            log.info("Status name: " + statusName + " not found");
            return false;
        }

        return true;
    }
}
