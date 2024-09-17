package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateUserRequestDTO;
import org.example.dto.requestdto.PasswordChangeRequestDTO;
import org.example.dto.requestdto.ProfileUpdateRequestDTO;
import org.example.dto.requestdto.UpdateUserRequestDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();

        log.info("Users successfully received");

        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/users/{id}")
    @Validated
    public ResponseEntity<UserResponseDTO> getUserById(@NotNull @PathVariable Integer id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);

        log.info("User: " + id + " successfully received");

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/admin/users/profile")
    public ResponseEntity<UserResponseDTO> getAdminProfile(Authentication authentication) {
        String username = authentication.getName();

        UserResponseDTO userResponseDTO = userService.findUserByUsername(username)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow();

        log.info("Admin " + username + " profile successfully received");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);

        log.info("User: " + userRequestDTO + " created successfully");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/admin/users/{id}")
    @Validated
    public ResponseEntity<UserResponseDTO> updateUserById(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody UpdateUserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUserByIdForAdmin(id, userRequestDTO);

        log.info("The User: " + id + " has successfully changed");

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/admin/users/change-password/{id}")
    @Validated
    public ResponseEntity<String> changePasswordForAdmin(@NotNull @PathVariable Integer id,
                                                         @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        userService.changePassword(id, passwordChangeRequestDTO);

        log.info("For Admin: " + id + " password changed successfully");

        return ResponseEntity.ok("Password changed successfully");
    }

    @PutMapping("/admin/users/update-profile/{id}")
    @Validated
    public ResponseEntity<String> updateProfileForAdmin(@NotNull @PathVariable Integer id,
                                                        @RequestBody ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        userService.updateProfile(id, profileUpdateRequestDTO);

        log.info("For Admin: " + id + " profile updated successfully");

        return ResponseEntity.ok("Profile updated successfully");
    }

    @DeleteMapping("/admin/users/{id}")
    @Validated
    public ResponseEntity<Void> deleteUser(@NotNull @PathVariable Integer id) {
        userService.deleteUser(id);

        log.info("The User: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/users/{id}/status")
    @Validated
    public ResponseEntity<String> updateUserStatus(@NotNull @PathVariable Integer id,
                                                   @NotNull @RequestParam String status) {
        userService.updateUserStatus(id, status);

        log.info("The User: " + id + " has successfully updated status to " + status);

        return ResponseEntity.ok("User status updated to " + status);
    }

    @GetMapping("/client/users/profile")
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
    public ResponseEntity<String> changePasswordForClient(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        userService.changePassword(id, passwordChangeRequestDTO);

        log.info("For Client: " + id + " password changed successfully");

        return ResponseEntity.ok("Password changed successfully");
    }

    @PutMapping("/client/users/update-profile/{id}")
    @Validated
    public ResponseEntity<String> updateProfileForClient(@NotNull @PathVariable Integer id,
                                                         @RequestBody ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        userService.updateProfile(id, profileUpdateRequestDTO);

        log.info("For Client: " + id + " profile updated successfully");

        return ResponseEntity.ok("Profile updated successfully");
    }
}
