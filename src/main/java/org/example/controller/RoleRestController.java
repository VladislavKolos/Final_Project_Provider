package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.RoleRequestDTO;
import org.example.dto.responsedto.RoleResponseDTO;
import org.example.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user roles.
 * This class provides an API for managing user roles, including getting, creating, updating, and deleting.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Operations related to managing roles (Admin)")
public class RoleRestController {

    private final RoleService roleService;

    @ExecutionTime
    @GetMapping
    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> rolesList = roleService.getAllRoles();

        log.info("Roles successfully received");

        return ResponseEntity.ok(rolesList);
    }

    @ExecutionTime
    @GetMapping("/{id}")
    @Validated
    @Operation(summary = "Get a role by ID", description = "Retrieves a role by its unique identifier")
    @Parameter(name = "id", description = "Unique identifier of the role")
    public ResponseEntity<RoleResponseDTO> getRoleById(@NotNull @PathVariable Integer id) {
        RoleResponseDTO roleResponseDTO = roleService.getRoleById(id);

        log.info("Role: " + id + " successfully received");

        return ResponseEntity.ok(roleResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a new role")
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO roleResponseDTO = roleService.createRole(roleRequestDTO);

        log.info("Role: " + roleRequestDTO + " created successfully");

        return ResponseEntity.ok(roleResponseDTO);
    }

    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Update a role", description = "Updates a role by its ID")
    @Parameter(name = "id", description = "Unique identifier of the role")
    public ResponseEntity<RoleResponseDTO> updateRole(@NotNull @PathVariable Integer id,
                                                      @Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        RoleResponseDTO roleResponseDTO = roleService.updateRole(id, roleRequestDTO);

        log.info("The role: " + id + " has successfully changed its name");

        return ResponseEntity.ok(roleResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Validated
    @Operation(summary = "Delete a role", description = "Deletes a role by its ID")
    @Parameter(name = "id", description = "Unique identifier of the role")
    public ResponseEntity<Void> deleteRole(@NotNull @PathVariable Integer id) {
        roleService.deleteRole(id);

        log.info("The role: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }
}
