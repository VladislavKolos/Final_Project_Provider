package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.RoleRequestDTO;
import org.example.dto.responsedto.RoleResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with roles.
 * This service provides methods for obtaining, creating, updating, and deleting roles.
 * It interacts with the role repository (`RoleRepository`), mapper (`RoleMapper`) to transform objects.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final MessageSource messageSource;

    /**
     * This method looks up the role entity in the `roleRepository` repository using the specified identifier.
     * If the entity is found, it is returned. If the entity is not found, an `EntityNotFoundException` exception is thrown.
     *
     * @param id Role ID
     * @return The role entity, if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public Role getRoleEntityById(Integer id) {
        return roleRepository.findById(id).orElseThrow();
    }

    /**
     * This method searches for all roles in the `roleRepository` repository
     * and returns them as a list of `RoleResponseDTO` objects mapped using the `roleMapper` mapper.
     *
     * @return A list of `RoleResponseDTO` objects with information about all roles.
     */
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponseDTO)
                .toList();
    }

    /**
     * This method searches for a role in the `roleRepository` repository by the specified identifier.
     * If the role is found, it is converted to a `RoleResponseDTO` object using the `roleMapper` mapper and returned.
     * If the role is not found, a `ProviderNotFoundException` is thrown.
     *
     * @param id Role ID
     * @return A `RoleResponseDTO` object with information about the role, if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(roleMapper::toRoleResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "role.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method creates a new role based on the data from `RoleRequestDTO`,
     * stores it in the database and returns a `RoleResponseDTO` object with information about the created role.
     *
     * @param roleRequestDTO DTO object `RoleRequestDTO` containing information about the role being created.
     * @return A `RoleResponseDTO` object containing information about the created role.
     */
    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        return Optional.of(roleRequestDTO)
                .map(roleMapper::toRoleForCreate)
                .map(roleRepository::save)
                .map(roleMapper::toRoleResponseDTO)
                .orElseThrow();
    }

    /**
     * This method updates an existing role with the specified ID.
     * It first finds the role in the repository, then updates its name using the data from `RoleRequestDTO` and stores the updated role in the database.
     *
     * @param id             Role ID
     * @param roleRequestDTO DTO object `RoleRequestDTO` containing information for updating the role.
     * @return A `RoleResponseDTO` object with information about the updated role.
     */
    @Transactional
    public RoleResponseDTO updateRole(Integer id, RoleRequestDTO roleRequestDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "role.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        role.setName(roleRequestDTO.getName());

        return Optional.of(role)
                .map(roleRepository::save)
                .map(roleMapper::toRoleResponseDTO)
                .orElseThrow();
    }

    /**
     * This method removes the role with the specified ID from the database.
     *
     * @param id Role ID
     */
    @Transactional
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "role.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        roleRepository.delete(role);
    }
}
