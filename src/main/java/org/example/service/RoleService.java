package org.example.service;

import org.example.dto.requestdto.RoleRequestDTO;
import org.example.dto.responsedto.RoleResponseDTO;
import org.example.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Provides methods for creating, retrieving, updating, and deleting roles.
 */
@Component
public interface RoleService {
    Role getRoleEntityById(Integer id);

    List<RoleResponseDTO> getAllRoles();

    RoleResponseDTO getRoleById(Integer id);

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    RoleResponseDTO updateRole(Integer id, RoleRequestDTO roleRequestDTO);

    void deleteRole(Integer id);
}
