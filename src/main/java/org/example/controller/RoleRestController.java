package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistRoleId;
import org.example.dto.requestdto.RoleRequestDTO;
import org.example.dto.responsedto.RoleResponseDTO;
import org.example.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> rolesList = roleService.getAllRoles();

        log.info("Roles successfully received");

        return ResponseEntity.ok(rolesList);
    }

    @GetMapping("/{id}")
    @Validated
    public ResponseEntity<RoleResponseDTO> getRoleById(@NotNull @ExistRoleId @PathVariable Integer id) {
        RoleResponseDTO roleResponseDTO = roleService.getRoleById(id);

        log.info("Role: " + id + " successfully received");

        return ResponseEntity.ok(roleResponseDTO);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO roleResponseDTO = roleService.createRole(roleRequestDTO);

        log.info("Role: " + roleRequestDTO + " created successfully");

        return ResponseEntity.ok(roleResponseDTO);
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<RoleResponseDTO> updateRole(@NotNull @ExistRoleId @PathVariable Integer id,
                                                      @Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        RoleResponseDTO roleResponseDTO = roleService.updateRole(id, roleRequestDTO);

        log.info("The role: " + id + " has successfully changed its name");

        return ResponseEntity.ok(roleResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Validated
    public ResponseEntity<Void> deleteRole(@NotNull @ExistRoleId @PathVariable Integer id) {
        roleService.deleteRole(id);

        log.info("The role: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }
}
