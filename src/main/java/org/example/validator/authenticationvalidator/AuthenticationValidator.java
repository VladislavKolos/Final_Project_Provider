package org.example.validator.authenticationvalidator;

import lombok.RequiredArgsConstructor;
import org.example.dto.requestdto.RegisterRequestDTO;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.ProviderConstantUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Authentication data validator.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationValidator {

    private final UserService userService;

    /**
     * Check if the user is blocked by email.
     *
     * @param registerRequestDTO DTO-объект регистрации.
     * @return true - if the user is blocked, false - otherwise.
     */

    public boolean checkUserByEmailIsBanned(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUserByEmail = userService.findUserByEmail(registerRequestDTO.getEmail());

        return existingUserByEmail.isPresent() && existingUserByEmail.get()
                .getStatus()
                .getId() == ProviderConstantUtil.USER_STATUS_BANNED;
    }

    /**
     * Check if the user is blocked by phone number.
     *
     * @param registerRequestDTO Registration DTO object.
     * @return true - if the user is blocked, false - otherwise.
     */
    public boolean checkUserByPhoneIsBanned(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUserByPhone = userService.findUserByPhone(registerRequestDTO.getPhone());

        return existingUserByPhone.isPresent() && existingUserByPhone.get()
                .getStatus()
                .getId() == ProviderConstantUtil.USER_STATUS_BANNED;
    }
}
