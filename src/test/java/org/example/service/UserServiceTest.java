package org.example.service;

import org.example.dto.requestdto.ProfileUpdateRequestDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.impl.UserServiceImpl;
import org.example.validator.uservalidator.UserRequestDTOValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRequestDTOValidator dtoValidator;

    @Mock
    private EmailTokenService emailTokenService;

    private static final ProfileUpdateRequestDTO requestDto = createRequestDto();

    @Test
    public void testUpdateProfileSuccess() {
        int userId = 1;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(dtoValidator.existsEmail(requestDto.getEmail())).thenReturn(false);
        when(dtoValidator.existsUsername(requestDto.getUsername())).thenReturn(false);
        when(dtoValidator.existsPhone(requestDto.getPhone())).thenReturn(false);

        doNothing().when(emailTokenService).sendConfirmationEmail(any(), any(), any(), any());

        userService.updateProfile(userId, requestDto);

        verify(emailTokenService).sendConfirmationEmail(any(),
                eq(requestDto.getEmail()),
                eq(requestDto.getUsername()),
                eq(requestDto.getPhone()));

        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateProfileUserNotFound() {
        int userId = new Random().nextInt(Integer.MAX_VALUE);

        when(userRepository.findById(userId)).thenThrow(ProviderNotFoundException.class);

        assertThrows(ProviderNotFoundException.class, () -> userService.updateProfile(userId, requestDto));
    }

    private static ProfileUpdateRequestDTO createRequestDto() {
        return ProfileUpdateRequestDTO.builder()
                .email("vladislav.kolos.y@gmail.com")
                .username("New_User")
                .phone("+375299914654")
                .build();
    }
}
