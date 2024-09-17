package org.example.validator.authenticationvalidator;

import lombok.RequiredArgsConstructor;
import org.example.dto.requestdto.RegisterRequestDTO;
import org.example.model.User;
import org.example.service.UserService;
import org.example.util.ProviderConstantUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationValidator {

    private final UserService userService;

    public boolean checkUserByEmailIsBanned(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUserByEmail = userService.findUserByEmail(registerRequestDTO.getEmail());

        return existingUserByEmail.isPresent() && existingUserByEmail.get()
                .getStatus()
                .getId() == ProviderConstantUtil.USER_STATUS_BANNED;
    }

    public boolean checkUserByPhoneIsBanned(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUserByPhone = userService.findUserByPhone(registerRequestDTO.getPhone());

        return existingUserByPhone.isPresent() && existingUserByPhone.get()
                .getStatus()
                .getId() == ProviderConstantUtil.USER_STATUS_BANNED;
    }
}
