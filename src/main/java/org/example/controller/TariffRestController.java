package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.requestdto.UpdateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.service.TariffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing tariffs.
 * This class provides an API for managing tariffs, including getting, creating, updating, and deleting (available only to the administrator),
 * as well as getting a list of rates for a client.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Tariffs", description = "Operations related to managing tariffs (Admin and Client)")
public class TariffRestController {

    private final TariffService tariffService;

    @ExecutionTime
    @GetMapping("/admin/tariffs")
    @Validated
    @Operation(summary = "Get all tariffs for Admin (paginated)", description = "Retrieves a paginated list of all tariffs (for Admin)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=name,asc)")
    public ResponseEntity<Page<TariffResponseDTO>> getAllTariffsForAdmin(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, value = 5)
                                                                         Pageable pageable) {
        Page<TariffResponseDTO> tariffs = tariffService.getAllTariffs(pageable);

        log.info("Tariffs for Admin successfully received");

        return ResponseEntity.ok(tariffs);
    }

    @ExecutionTime
    @GetMapping("/admin/tariffs/{id}")
    @Validated
    @Operation(summary = "Get a tariff by ID for Admin", description = "Retrieves a tariff by its unique identifier (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the tariff")
    public ResponseEntity<TariffResponseDTO> getTariffByIdForAdmin(@NotNull @PathVariable Integer id) {
        TariffResponseDTO tariffResponseDTO = tariffService.getTariffById(id);

        log.info("Tariff: " + id + " for Admin successfully received");

        return ResponseEntity.ok(tariffResponseDTO);
    }

    @PostMapping("/admin/tariffs")
    @Operation(summary = "Create a new tariff (Admin)", description = "Creates a new tariff (for Admin)")
    public ResponseEntity<TariffResponseDTO> createTariff(@Valid @RequestBody CreateTariffRequestDTO createTariffRequestDTO) {
        TariffResponseDTO tariffResponseDTO = tariffService.createTariff(createTariffRequestDTO);

        log.info("Tariff: " + createTariffRequestDTO + " created successfully");

        return ResponseEntity.ok(tariffResponseDTO);
    }

    @PutMapping("/admin/tariffs/{id}")
    @Validated
    @Operation(summary = "Update a tariff (Admin)", description = "Updates a tariff by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the tariff")
    public ResponseEntity<TariffResponseDTO> updateTariff(@NotNull @PathVariable Integer id,
                                                          @Valid @RequestBody UpdateTariffRequestDTO updateTariffRequestDTO) {
        TariffResponseDTO tariffResponseDTO = tariffService.updateTariff(id, updateTariffRequestDTO);

        log.info("The tariff: " + id + " has successfully changed");

        return ResponseEntity.ok(tariffResponseDTO);
    }

    @DeleteMapping("/admin/tariffs/{id}")
    @Validated
    @Operation(summary = "Delete a tariff (Admin)", description = "Deletes a tariff by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the tariff")
    public ResponseEntity<Void> deleteTariff(@NotNull @PathVariable Integer id) {
        tariffService.deleteTariff(id);

        log.info("The tariff: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/tariffs")
    @Validated
    @Operation(summary = "Get all tariffs for Client (paginated)", description = "Retrieves a paginated list of all tariffs (for Client)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=name,asc)")
    public ResponseEntity<Page<TariffResponseDTO>> getAllTariffsForClient(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, value = 5)
                                                                          Pageable pageable) {
        Page<TariffResponseDTO> tariffs = tariffService.getAllTariffs(pageable);

        log.info("Tariffs for Client successfully received");

        return ResponseEntity.ok(tariffs);
    }

    @ExecutionTime
    @GetMapping("/client/tariffs/{id}")
    @Validated
    @Operation(summary = "Get a tariff by ID for Client", description = "Retrieves a tariff by its unique identifier (for Client)")
    @Parameter(name = "id", description = "Unique identifier of the tariff")
    public ResponseEntity<TariffResponseDTO> getTariffByIdForClient(@NotNull @PathVariable Integer id) {
        TariffResponseDTO tariffResponseDTO = tariffService.getTariffById(id);

        log.info("Tariff: " + id + " for Client successfully received");

        return ResponseEntity.ok(tariffResponseDTO);
    }
}
