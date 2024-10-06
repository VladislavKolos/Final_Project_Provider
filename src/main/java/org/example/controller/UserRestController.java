package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.CreateUserRequestDTO;
import org.example.dto.requestdto.PasswordChangeRequestDTO;
import org.example.dto.requestdto.ProfileUpdateRequestDTO;
import org.example.dto.requestdto.UpdateUserRequestDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.exception.ProviderAccessDeniedException;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.util.RecipientCurrentClientUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user management.
 * This class provides an API for user management, including getting, creating, updating, deleting,
 * changing password and updating profile (for administrator and client),
 * updating user status (available only to administrator),
 * getting profiles of authorized administrators and clients,
 * confirming user email address
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to managing users (Admin adn Client)")
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final MessageSource messageSource;

    @ExecutionTime
    @GetMapping("/admin/users")
    @Operation(summary = "Get all users for Admin", description = "Retrieves a list of all users (for Admin)")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();

        log.info("Users successfully received");

        return ResponseEntity.ok(users);
    }

    @ExecutionTime
    @GetMapping("/admin/users/{id}")
    @Validated
    @Operation(summary = "Get a user by ID for Admin", description = "Retrieves a user by its unique identifier (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the user")
    public ResponseEntity<UserResponseDTO> getUserById(@NotNull @PathVariable Integer id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);

        log.info("User: " + id + " successfully received");

        return ResponseEntity.ok(userResponseDTO);
    }

    @ExecutionTime
    @GetMapping("/admin/users/profile")
    @Operation(summary = "Get Admin profile", description = "Retrieves the current Admin's profile information")
    public ResponseEntity<UserResponseDTO> getAdminProfile(Authentication authentication) {
        String username = authentication.getName();

        UserResponseDTO userResponseDTO = userService.findUserByUsername(username)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow();

        log.info("Admin " + username + " profile successfully received");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/admin/users")
    @Operation(summary = "Create a new user (Admin)", description = "Creates a new user (for Admin)")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);

        log.info("User: " + userRequestDTO + " created successfully");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/admin/users/{id}")
    @Validated
    @Operation(summary = "Update a user by ID for Admin", description = "Updates a user by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the user")
    public ResponseEntity<UserResponseDTO> updateUserById(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody UpdateUserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUserByIdForAdmin(id, userRequestDTO);

        log.info("The User: " + id + " has successfully changed");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/admin/users/change-password/{id}")
    @Validated
    @Operation(summary = "Change password for a user (Admin)", description = "Allows the administrator to change their own password")
    @Parameter(name = "id", description = "Unique identifier of the user")
    public ResponseEntity<String> changePasswordForAdmin(@NotNull @PathVariable Integer id,
                                                         @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        userService.changePassword(id, passwordChangeRequestDTO);

        log.info("For Admin: " + id + " password changed successfully");

        return ResponseEntity.ok("Password changed successfully");
    }

    @PutMapping("/admin/users/update-profile/{id}")
    @Validated
    @Operation(summary = "Update user profile for Admin", description = "Allows the administrator to update their profile")
    @Parameter(name = "id", description = "Unique identifier of the user")
    public ResponseEntity<String> updateProfileForAdmin(@NotNull @PathVariable Integer id,
                                                        @RequestBody ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        userService.updateProfile(id, profileUpdateRequestDTO);

        log.info("For Admin: " + id + " An email confirmation email has been sent to");

        return ResponseEntity.ok("An email to confirm your email address has been sent to your email");
    }

    @DeleteMapping("/admin/users/{id}")
    @Validated
    @Operation(summary = "Delete a user (Admin)", description = "Deletes a user by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the user")
    public ResponseEntity<Void> deleteUser(@NotNull @PathVariable Integer id) {
        userService.deleteUser(id);

        log.info("The User: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/users/{id}/status")
    @Validated
    @Operation(summary = "Update user status (Admin)", description = "Updates the status of a user by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the user")
    @Parameter(name = "status", description = "New status for the user")
    public ResponseEntity<String> updateUserStatus(@NotNull @PathVariable Integer id,
                                                   @NotNull @RequestParam String status) {
        userService.updateUserStatus(id, status);

        log.info("The User: " + id + " has successfully updated status to " + status);

        return ResponseEntity.ok("User status updated to " + status);
    }

    @ExecutionTime
    @GetMapping("/client/users/profile")
    @Operation(summary = "Get client profile", description = "Retrieves the current client's profile information")
    public ResponseEntity<UserResponseDTO> getClientProfile(Authentication authentication) {
        String username = authentication.getName();

        UserResponseDTO userResponseDTO = userService.findUserByUsername(username)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow();

        log.info("Client " + username + " profile successfully received");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/client/users/change-password/{id}")
    @Validated
    @Operation(summary = "Change password for client", description = "Allows a client to change their own password")
    @Parameter(name = "id", description = "Unique identifier of the client (should match the authenticated client)")
    public ResponseEntity<String> changePasswordForClient(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {

        if (RecipientCurrentClientUtil.getCurrentClientId() != id) {
            throw new ProviderAccessDeniedException(messageSource.getMessage("user.error.id_does_not_have_access_rights",
                    null,
                    LocaleContextHolder.getLocale()));
        }

        userService.changePassword(id, passwordChangeRequestDTO);

        log.info("For Client: " + id + " password changed successfully");

        return ResponseEntity.ok("Password changed successfully");
    }

    @PutMapping("/client/users/update-profile/{id}")
    @Validated
    @Operation(summary = "Update client profile", description = "Allows a client to update their own profile")
    @Parameter(name = "id", description = "Unique identifier of the client (should match the authenticated client)")
    public ResponseEntity<String> updateProfileForClient(@NotNull @PathVariable Integer id,
                                                         @RequestBody ProfileUpdateRequestDTO profileUpdateRequestDTO) {

        if (RecipientCurrentClientUtil.getCurrentClientId() != id) {
            throw new ProviderAccessDeniedException(messageSource.getMessage("user.error.id_does_not_have_access_rights",
                    null,
                    LocaleContextHolder.getLocale()));
        }

        userService.updateProfile(id, profileUpdateRequestDTO);

        log.info("For Client: " + id + " An email confirmation email has been sent to");

        return ResponseEntity.ok("An email to confirm your email address has been sent to your email");
    }

    @PostMapping("/users/confirm-email")
    @Operation(summary = "Confirm email", description = "Confirms an email address using a confirmation token")
    @Parameter(name = "token", description = "Confirmation token received via email")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        userService.confirmEmail(token);

        log.info("Email confirmed successfully");

        return ResponseEntity.ok("Email confirmed successfully");
    }
}
