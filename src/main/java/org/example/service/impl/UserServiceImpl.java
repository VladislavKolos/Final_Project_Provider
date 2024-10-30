package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateUserRequestDTO;
import org.example.dto.requestdto.PasswordChangeRequestDTO;
import org.example.dto.requestdto.ProfileUpdateRequestDTO;
import org.example.dto.requestdto.UpdateUserRequestDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.exception.ProviderAccessDeniedException;
import org.example.exception.ProviderConflictException;
import org.example.exception.ProviderNotFoundException;
import org.example.exception.ProviderTokenException;
import org.example.mapper.UserMapper;
import org.example.model.EmailToken;
import org.example.model.Status;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.EmailTokenService;
import org.example.service.RoleService;
import org.example.service.StatusService;
import org.example.service.UserService;
import org.example.util.RecipientCurrentClientUtil;
import org.example.validator.uservalidator.UserRequestDTOValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for working with users.
 * This service provides methods for creating, getting, updating, deleting users, and confirming users email.
 * It interacts with the user repository (`UserRepository`), validator of requests for creating/updating users (`UserRequestDTOValidator`),
 * service for working with roles (`RoleService`), service for working with statuses (`StatusService`), password encoder (` PasswordEncoder`),
 * a service for sending mail (`EmailService`) and a mapper (`UserMapper`) for converting objects.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final StatusService statusService;

    private final UserRequestDTOValidator userRequestDTOValidator;

    private final EmailTokenService emailTokenService;

    private final MessageSource messageSource;

    /**
     * This method persists the provided `user` entity to the database.
     *
     * @param user The user entity to be saved.
     */
    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * This method fetches a user entity from the database using the provided ID.
     * If the user is not found, an exception is thrown.
     *
     * @param id User ID
     * @return The user entity, or throws an exception if not found.
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    /**
     * This method searches for a user in the database based on the provided username.
     * It returns an `Optional<User>` object, which may be empty if no user is found with the given username.
     *
     * @param username The username to search for.
     * @return An `Optional<User>` containing the user if found, or empty if not found.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * This method searches for a user in the database based on the provided email.
     * It returns an `Optional<User>` object, which may be empty if no user is found with the given email.
     *
     * @param email The email to search for.
     * @return An `Optional<User>` containing the user if found, or empty if not found.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * This method searches for a user in the database based on the provided phone.
     * It returns an `Optional<User>` object, which may be empty if no user is found with the given phone.
     *
     * @param phone The phone to search for.
     * @return An `Optional<User>` containing the user if found, or empty if not found.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    /**
     * This method fetches all user entities from the database and maps them to `UserResponseDTO` objects before returning the list.
     *
     * @return A list of `UserResponseDTO` objects representing all users.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseDTO).toList();
    }

    /**
     * This method fetches a user entity from the database using the provided ID and maps it to a `UserResponseDTO`.
     * If the user is not found, an exception is thrown.
     *
     * @param id User ID
     * @return The user as a response DTO, or throws an exception if not found.
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("user.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method validates the provided user information, creates a new user entity,
     * persists it to the database, and returns a response DTO representing the created user.
     *
     * @param createUserRequestDTO The DTO containing the information for the new user.
     * @return The created user as a response DTO.
     */
    @Override
    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        if (userRequestDTOValidator.existsUsername(createUserRequestDTO.getUsername())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.username_exists",
                    new Object[]{createUserRequestDTO.getUsername()},
                    LocaleContextHolder.getLocale()));

        } else if (userRequestDTOValidator.existsEmail(createUserRequestDTO.getEmail())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.email_exists",
                    new Object[]{createUserRequestDTO.getEmail()},
                    LocaleContextHolder.getLocale()));

        } else if (userRequestDTOValidator.existsPhone(createUserRequestDTO.getPhone())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.phone_exists",
                    new Object[]{createUserRequestDTO.getPhone()},
                    LocaleContextHolder.getLocale()));
        }

        User user = buildUser(createUserRequestDTO);

        return Optional.of(user).map(userRepository::save).map(userMapper::toUserResponseDTO).orElseThrow();
    }

    /**
     * This method retrieves the user with the specified ID, validates the provided update data,
     * updates the user's information, and saves the updated user to the database.
     *
     * @param id             User ID
     * @param userRequestDTO The DTO containing the updated user information.
     * @return The updated user as a response DTO.
     */
    @Override
    @Transactional
    public UserResponseDTO updateUserByIdForAdmin(Integer id, UpdateUserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("user.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        if (userRequestDTOValidator.existsUsername(userRequestDTO.getUsername()) && !user.getUsername()
                .equals(userRequestDTO.getUsername())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.username_exists",
                    new Object[]{userRequestDTO.getUsername()},
                    LocaleContextHolder.getLocale()));

        } else if (userRequestDTOValidator.existsEmail(userRequestDTO.getEmail()) && !user.getEmail()
                .equals(userRequestDTO.getEmail())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.email_exists",
                    new Object[]{userRequestDTO.getEmail()},
                    LocaleContextHolder.getLocale()));

        } else if (userRequestDTOValidator.existsPhone(userRequestDTO.getPhone()) && !user.getPhone()
                .equals(userRequestDTO.getPhone())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.phone_exists",
                    new Object[]{userRequestDTO.getPhone()},
                    LocaleContextHolder.getLocale()));
        }

        setUser(user, userRequestDTO);

        return Optional.of(user).map(userRepository::save).map(userMapper::toUserResponseDTO).orElseThrow();
    }

    /**
     * This method validates the provided old and new passwords, updates the user's password
     * with the new password, and saves the updated user to the database.
     *
     * @param id                       The ID of the User whose password needs to be changed.
     * @param passwordChangeRequestDTO The DTO containing the old password and new password information.
     */
    @Override
    @Transactional
    public void changePassword(Integer id, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("user.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        if (!passwordEncoder.matches(passwordChangeRequestDTO.getOldPassword(), user.getPassword())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.old_password_incorrect",
                    null,
                    LocaleContextHolder.getLocale()));
        }

        if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmNewPassword())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.passwords_do_not_match",
                    null,
                    LocaleContextHolder.getLocale()));
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * This method allows users to update their username, email, and phone number.
     * It performs validation to ensure the username, email, and phone number don't already
     * exist for another user. It also handles sending a confirmation email if the email address is changed.
     *
     * @param id                      The ID of the User whose profile needs to be updated.
     * @param profileUpdateRequestDTO The DTO containing the updated profile information.
     */
    @Override
    @Transactional
    public void updateProfile(Integer id, ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("user.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        if (userRequestDTOValidator.existsUsername(profileUpdateRequestDTO.getUsername())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.username_exists",
                    new Object[]{profileUpdateRequestDTO.getUsername()},
                    LocaleContextHolder.getLocale()));

        } else if (userRequestDTOValidator.existsEmail(profileUpdateRequestDTO.getEmail())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.email_exists",
                    new Object[]{profileUpdateRequestDTO.getEmail()},
                    LocaleContextHolder.getLocale()));

        } else if (userRequestDTOValidator.existsPhone(profileUpdateRequestDTO.getPhone())) {
            throw new ProviderConflictException(messageSource.getMessage("user.error.phone_exists",
                    new Object[]{profileUpdateRequestDTO.getPhone()},
                    LocaleContextHolder.getLocale()));
        }

        String email = (profileUpdateRequestDTO.getEmail() != null) ? profileUpdateRequestDTO.getEmail() : user.getEmail();
        String username = (profileUpdateRequestDTO.getUsername() != null) ? profileUpdateRequestDTO.getUsername() : user.getUsername();
        String phone = (profileUpdateRequestDTO.getPhone() != null) ? profileUpdateRequestDTO.getPhone() : user.getPhone();

        emailTokenService.sendConfirmationEmail(user, email, username, phone);

        userRepository.save(user);
    }

    /**
     * This method deletes the user with the specified ID from the database.
     *
     * @param id User ID
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        if (id == RecipientCurrentClientUtil.getCurrentClientId()) {
            throw new ProviderAccessDeniedException("Admin cannot delete his own account");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("user.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        userRepository.delete(user);
    }

    /**
     * This method updates the status of the user with the specified ID based on the provided status name.
     * It retrieves the user and status objects from their respective repositories and validates their existence.
     *
     * @param id         The ID of the User whose status needs to be updated.
     * @param statusName The name of the new status for the user.
     */
    @Override
    @Transactional
    public void updateUserStatus(Integer id, String statusName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("user.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        Status status = statusService.getStatusByName(statusName)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "status.error.not_found.by_name",
                        new Object[]{statusName},
                        LocaleContextHolder.getLocale())));

        user.setStatus(status);
        userRepository.save(user);
    }

    /**
     * This method retrieves an `EmailToken` object based on the provided token string.
     * It validates if the token is valid (not expired) and then updates the user's email address
     * with the email address associated with the token.
     *
     * @param token token The confirmation token string received by the user.
     */
    @Override
    @Transactional
    public void confirmEmail(String token) {
        EmailToken emailToken = emailTokenService.findByToken(token);

        if (emailToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ProviderTokenException(messageSource.getMessage("auth.error.token_expired",
                    null,
                    LocaleContextHolder.getLocale()));
        }

        User user = emailToken.getUser();

        user.setEmail(emailToken.getEmail());
        user.setUsername(emailToken.getUsername());
        user.setPhone(emailToken.getPhone());
        userRepository.save(user);
    }

    /**
     * This private helper method creates a new `User` object using the builder pattern,
     * setting its properties based on the provided request DTO. It retrieves the status and role
     * entities using the `statusService` and `roleService`, respectively.
     *
     * @param createUserRequestDTO The DTO containing the information for the new user.
     * @return The newly created `User` entity.
     */
    private User buildUser(CreateUserRequestDTO createUserRequestDTO) {
        return User.builder()
                .username(createUserRequestDTO.getUsername())
                .password(passwordEncoder.encode(createUserRequestDTO.getPassword()))
                .email(createUserRequestDTO.getEmail())
                .phone(createUserRequestDTO.getPhone())
                .status(statusService.getStatusEntityById(createUserRequestDTO.getStatusId()))
                .role(roleService.getRoleEntityById(createUserRequestDTO.getRoleId()))
                .build();
    }

    /**
     * This private helper method updates the user's username, email, phone, status, and role
     * based on the provided `UpdateUserRequestDTO`. If a new password is provided, it is also
     * encoded and set for the user.
     *
     * @param user           The user entity to be updated.
     * @param userRequestDTO The DTO containing the updated user information.
     */
    private void setUser(User user, UpdateUserRequestDTO userRequestDTO) {
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setStatus(statusService.getStatusEntityById(userRequestDTO.getStatusId()));
        user.setRole(roleService.getRoleEntityById(userRequestDTO.getRoleId()));

        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
    }
}
