package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.NoExistTariffName;
import org.example.repository.TariffRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoExistTariffNameValidator implements ConstraintValidator<NoExistTariffName, String> {

    private final TariffRepository tariffRepository;

    @Override
    public boolean isValid(String tariffName, ConstraintValidatorContext context) {
        if (tariffName != null && tariffRepository.existsByName(tariffName)) {
            log.info("A tariff with the same name: " + tariffName + " already exists");
            return false;
        }
        return true;
    }
}
