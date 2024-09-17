package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.StatusRequestDTO;
import org.example.dto.responsedto.StatusResponseDTO;
import org.example.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/statuses")
@RequiredArgsConstructor
public class StatusRestController {

    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusResponseDTO>> getAllStatuses() {
        List<StatusResponseDTO> statusesList = statusService.getAllStatuses();

        log.info("Statuses successfully received");

        return ResponseEntity.ok(statusesList);
    }

    @GetMapping("/{id}")
    @Validated
    public ResponseEntity<StatusResponseDTO> getStatusById(@NotNull @PathVariable Integer id) {
        StatusResponseDTO statusResponseDTO = statusService.getStatusById(id);

        log.info("Status: " + id + " successfully received");

        return ResponseEntity.ok(statusResponseDTO);
    }

    @PostMapping
    public ResponseEntity<StatusResponseDTO> createStatus(@Valid @RequestBody StatusRequestDTO statusRequestDTO) {
        StatusResponseDTO statusResponseDTO = statusService.createStatus(statusRequestDTO);

        log.info("Status: " + statusRequestDTO + " created successfully");

        return ResponseEntity.ok(statusResponseDTO);
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<StatusResponseDTO> updateStatus(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody StatusRequestDTO statusRequestDTO) {

        StatusResponseDTO statusResponseDTO = statusService.updateStatus(id, statusRequestDTO);

        log.info("The status: " + id + " has successfully changed its name");

        return ResponseEntity.ok(statusResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Validated
    public ResponseEntity<Void> deleteStatus(@NotNull @PathVariable Integer id) {
        statusService.deleteStatus(id);

        log.info("The status: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }
}
