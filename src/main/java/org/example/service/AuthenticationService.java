package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.AuthenticationRequestDTO;
import org.example.dto.requestdto.RegisterRequestDTO;
import org.example.dto.responsedto.AuthenticationResponseDTO;
import org.example.exception.ProviderBannedException;
import org.example.model.User;
import org.example.util.ProviderConstantUtil;
import org.example.validator.authenticationvalidator.AuthenticationValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        if (authenticationValidator.checkUserByEmailIsBanned(request)) {
            throw new ProviderBannedException("User with this email: " + request.getEmail() + " is banned and cannot register again");

        } else if (authenticationValidator.checkUserByPhoneIsBanned(request)) {
            throw new ProviderBannedException("User with this phone: " + request.getPhone() + " number is banned and cannot register again");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(statusService.getStatusEntityById(ProviderConstantUtil.USER_STATUS_ACTIVE))
                .role(roleService.getRoleEntityById(ProviderConstantUtil.ROLE_CLIENT))
                .build();

        userService.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        User user = userService.findUserByUsername(request.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}

