package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistTariffId;
import org.example.repository.TariffRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExistTariffIdValidator implements ConstraintValidator<ExistTariffId, Integer> {

    private final TariffRepository tariffRepository;

    @Override
    public boolean isValid(Integer tariffId, ConstraintValidatorContext context) {
        if (tariffId != null && !tariffRepository.existsById(tariffId)) {
            log.info("Tariff ID: " + tariffId + " not found");
            return false;
        }
        return true;
    }
}
