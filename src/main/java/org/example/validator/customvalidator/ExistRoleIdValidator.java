package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistRoleId;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExistRoleIdValidator implements ConstraintValidator<ExistRoleId, Integer> {

    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(Integer roleId, ConstraintValidatorContext context) {
        if (roleId != null && !roleRepository.existsById(roleId)) {
            log.info("Role ID: " + roleId + " not found");
            return false;
        }
        return true;
    }
}
