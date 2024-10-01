package org.example.service;

import org.example.dto.requestdto.CreateUserRequestDTO;
import org.example.dto.requestdto.PasswordChangeRequestDTO;
import org.example.dto.requestdto.ProfileUpdateRequestDTO;
import org.example.dto.requestdto.UpdateUserRequestDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods for creating, retrieving, updating, and deleting user accounts.
 * Also handles password changes, email confirmation, and status updates.
 */
@Component
public interface UserService {
    void save(User user);

    User getUserEntityById(Integer id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhone(String phone);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Integer id);

    UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);

    UserResponseDTO updateUserByIdForAdmin(Integer id, UpdateUserRequestDTO userRequestDTO);

    void changePassword(Integer id, PasswordChangeRequestDTO passwordChangeRequestDTO);

    void updateProfile(Integer id, ProfileUpdateRequestDTO profileUpdateRequestDTO);

    void deleteUser(Integer id);

    void updateUserStatus(Integer id, String statusName);

    void confirmEmail(String token);
}
