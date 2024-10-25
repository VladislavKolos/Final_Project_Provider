package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.StatusRequestDTO;
import org.example.dto.responsedto.StatusResponseDTO;
import org.example.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for status management.
 * This class provides an API for managing statuses, including getting, creating, updating, and deleting.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/statuses")
@RequiredArgsConstructor
@Tag(name = "Statuses", description = "Operations related to managing statuses (Admin)")
public class StatusRestController {

    private final StatusService statusService;

    @ExecutionTime
    @GetMapping
    @Operation(summary = "Get all statuses", description = "Retrieves a list of all statuses")
    public ResponseEntity<List<StatusResponseDTO>> getAllStatuses() {
        List<StatusResponseDTO> statusesList = statusService.getAllStatuses();

        log.info("Statuses successfully received");

        return ResponseEntity.ok(statusesList);
    }

    @ExecutionTime
    @GetMapping("/{id}")
    @Validated
    @Operation(summary = "Get a status by ID", description = "Retrieves a status by its unique identifier")
    @Parameter(name = "id", description = "Unique identifier of the status")
    public ResponseEntity<StatusResponseDTO> getStatusById(@NotNull @PathVariable Integer id) {
        StatusResponseDTO statusResponseDTO = statusService.getStatusById(id);

        log.info("Status: " + id + " successfully received");

        return ResponseEntity.ok(statusResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new status", description = "Creates a new status")
    public ResponseEntity<StatusResponseDTO> createStatus(@Valid @RequestBody StatusRequestDTO statusRequestDTO) {
        StatusResponseDTO statusResponseDTO = statusService.createStatus(statusRequestDTO);

        log.info("Status: " + statusRequestDTO + " created successfully");

        return ResponseEntity.ok(statusResponseDTO);
    }

    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Update a status", description = "Updates a status by its ID")
    @Parameter(name = "id", description = "Unique identifier of the status")
    public ResponseEntity<StatusResponseDTO> updateStatus(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody StatusRequestDTO statusRequestDTO) {

        StatusResponseDTO statusResponseDTO = statusService.updateStatus(id, statusRequestDTO);

        log.info("The status: " + id + " has successfully changed its name");

        return ResponseEntity.ok(statusResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Validated
    @Operation(summary = "Delete a status", description = "Deletes a status by its ID")
    @Parameter(name = "id", description = "Unique identifier of the status")
    public ResponseEntity<Void> deleteStatus(@NotNull @PathVariable Integer id) {
        statusService.deleteStatus(id);

        log.info("The status: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }
}
