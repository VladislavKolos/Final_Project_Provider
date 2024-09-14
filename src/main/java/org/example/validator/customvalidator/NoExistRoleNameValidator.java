package org.example.validator.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.NoExistRoleName;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoExistRoleNameValidator implements ConstraintValidator<NoExistRoleName, String> {

    private final RoleRepository roleRepository;


    @Override
    public boolean isValid(String roleName, ConstraintValidatorContext context) {
        if (roleName != null && roleRepository.existsByName(roleName)) {
            log.info("A role with the same name: " + roleName + " already exists");
            return false;
        }
        return true;
    }
}
