package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.AuthenticationRequestDTO;
import org.example.dto.requestdto.RegisterRequestDTO;
import org.example.dto.responsedto.AuthenticationResponseDTO;
import org.example.exception.ProviderBannedException;
import org.example.exception.ProviderNotFoundException;
import org.example.model.User;
import org.example.util.ProviderConstantUtil;
import org.example.validator.authenticationvalidator.AuthenticationValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User authentication service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final RoleService roleService;

    private final StatusService statusService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationValidator authenticationValidator;

    private final MessageSource messageSource;

    /**
     * New user registration.
     *
     * @param request request DTO object with user registration data.
     * @return AuthenticationResponseDTO DTO object with a JWT token.
     */
    @Transactional
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        if (authenticationValidator.checkUserByEmailIsBanned(request)) {
            throw new ProviderBannedException(messageSource.getMessage("user.error.banned.email",
                    new Object[]{request.getEmail()},
                    LocaleContextHolder.getLocale()));

        } else if (authenticationValidator.checkUserByPhoneIsBanned(request)) {
            throw new ProviderBannedException(messageSource.getMessage("user.error.banned.phone",
                    new Object[]{request.getPhone()},
                    LocaleContextHolder.getLocale()));
        }

        User user = buildUser(request);

        userService.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * User authentication.
     *
     * @param request request DTO object with data for user authentication.
     * @return AuthenticationResponseDTO DTO object with a JWT token.
     */
    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        User user = userService.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "user.error.not_found.by_username",
                        new Object[]{request.getUsername()},
                        LocaleContextHolder.getLocale())));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Create a user object from a registration DTO.
     *
     * @param request request DTO object with user registration data.
     * @return User - user object.
     */
    private User buildUser(RegisterRequestDTO request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(statusService.getStatusEntityById(ProviderConstantUtil.USER_STATUS_ACTIVE))
                .role(roleService.getRoleEntityById(ProviderConstantUtil.ROLE_CLIENT))
                .build();
    }
}

