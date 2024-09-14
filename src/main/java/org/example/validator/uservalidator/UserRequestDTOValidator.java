package org.example.validator.uservalidator;

import lombok.RequiredArgsConstructor;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestDTOValidator {

    private final UserRepository userRepository;

    public boolean noExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean noExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean noExistPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
