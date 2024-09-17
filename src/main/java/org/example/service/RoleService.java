package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.RoleRequestDTO;
import org.example.dto.responsedto.RoleResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public Role getRoleEntityById(Integer id) {
        return roleRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(roleMapper::toRoleResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException("Role: " + id + " not found"));
    }

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        return Optional.of(roleRequestDTO)
                .map(roleMapper::toRoleForCreate)
                .map(roleRepository::save)
                .map(roleMapper::toRoleResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public RoleResponseDTO updateRole(Integer id, RoleRequestDTO roleRequestDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Role: " + id + " not found"));

        role.setName(roleRequestDTO.getName());

        return Optional.of(role)
                .map(roleRepository::save)
                .map(roleMapper::toRoleResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Role: " + id + " not found"));

        roleRepository.delete(role);
    }
}
