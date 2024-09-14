package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateUserRequestDTO;
import org.example.dto.requestdto.PasswordChangeRequestDTO;
import org.example.dto.requestdto.ProfileUpdateRequestDTO;
import org.example.dto.requestdto.UpdateUserRequestDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.mapper.UserMapper;
import org.example.model.Status;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.validator.uservalidator.UserRequestDTOValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final StatusService statusService;

    private final UserRequestDTOValidator userRequestDTOValidator;

    private final EmailService emailService;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        if (userRequestDTOValidator.noExistUsername(createUserRequestDTO.getUsername())) {
            throw new RuntimeException("User with username: " + createUserRequestDTO.getUsername() + " already exists");
        } else if (userRequestDTOValidator.noExistEmail(createUserRequestDTO.getEmail())) {
            throw new RuntimeException("User with email: " + createUserRequestDTO.getEmail() + " already exists");
        } else if (userRequestDTOValidator.noExistPhone(createUserRequestDTO.getPhone())) {
            throw new RuntimeException("User with phone: " + createUserRequestDTO.getPhone() + " already exists");
        }

        User user = User.builder()
                .username(createUserRequestDTO.getUsername())
                .password(passwordEncoder.encode(createUserRequestDTO.getPassword()))
                .email(createUserRequestDTO.getEmail())
                .phone(createUserRequestDTO.getPhone())
                .status(statusService.getStatusEntityById(createUserRequestDTO.getStatusId()))
                .role(roleService.getRoleEntityById(createUserRequestDTO.getRoleId()))
                .build();

        return Optional.of(user)
                .map(userRepository::save)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public UserResponseDTO updateUserByIdForAdmin(Integer id, UpdateUserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRequestDTOValidator.noExistUsername(userRequestDTO.getUsername())) {
            throw new RuntimeException("User with username: " + userRequestDTO.getUsername() + " already exists");
        } else if (userRequestDTOValidator.noExistEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("User with email: " + userRequestDTO.getEmail() + " already exists");
        } else if (userRequestDTOValidator.noExistPhone(userRequestDTO.getPhone())) {
            throw new RuntimeException("User with phone: " + userRequestDTO.getPhone() + " already exists");
        }

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setStatus(statusService.getStatusEntityById(userRequestDTO.getStatusId()));
        user.setRole(roleService.getRoleEntityById(userRequestDTO.getRoleId()));

        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        return Optional.of(user)
                .map(userRepository::save)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void changePassword(Integer id, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(passwordChangeRequestDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmNewPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(Integer id, ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRequestDTOValidator.noExistUsername(profileUpdateRequestDTO.getUsername())) {
            throw new RuntimeException("User with username: " + profileUpdateRequestDTO.getUsername() + " already exists");
        } else if (userRequestDTOValidator.noExistEmail(profileUpdateRequestDTO.getEmail())) {
            throw new RuntimeException("User with email: " + profileUpdateRequestDTO.getEmail() + " already exists");
        } else if (userRequestDTOValidator.noExistPhone(profileUpdateRequestDTO.getPhone())) {
            throw new RuntimeException("User with phone: " + profileUpdateRequestDTO.getPhone() + " already exists");
        }

        if (profileUpdateRequestDTO.getUsername() != null) {
            user.setUsername(profileUpdateRequestDTO.getUsername());
        }

        if (profileUpdateRequestDTO.getEmail() != null) {
            user.setEmail(profileUpdateRequestDTO.getEmail());
            emailService.sendConfirmationEmail(user, user.getEmail());
        }

        if (profileUpdateRequestDTO.getPhone() != null) {
            user.setPhone(profileUpdateRequestDTO.getPhone());
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    @Transactional
    public void updateUserStatus(Integer id, String statusName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Status status = statusService.getStatusByName(statusName)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        user.setStatus(status);
        userRepository.save(user);
    }
}
