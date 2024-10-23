package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.CreatePromotionTariffRequestDTO;
import org.example.dto.requestdto.UpdatePromotionTariffRequestDTO;
import org.example.dto.responsedto.PromotionTariffResponseDTO;
import org.example.service.PromotionTariffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing promotional tariffs.
 * This class provides an API for managing promotional tariffs, including getting, creating, updating, and deleting.
 * It is divided into methods for administrator and client.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Promotional Tariffs", description = "Operations related to managing promotion tariffs (Admin and Client)")
public class PromotionTariffRestController {

    private final PromotionTariffService promotionTariffService;

    @ExecutionTime
    @GetMapping("/admin/promotions-tariffs")
    @Operation(summary = "Get all promotion tariffs for Admin", description = "Retrieves a paginated list of all promotion tariffs (for Admin)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=promotion.discountPercentage,asc)")
    public ResponseEntity<Page<PromotionTariffResponseDTO>> getAllPromotionsTariffsForAdmin(@PageableDefault(sort = "promotion.discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                                            Pageable pageable) {
        Page<PromotionTariffResponseDTO> promotionsTariffs = promotionTariffService.getAllPromotionsTariffs(pageable);

        log.info("Promotional tariff for Admin successfully received");

        return ResponseEntity.ok(promotionsTariffs);
    }

    @ExecutionTime
    @GetMapping("/admin/promotions-tariffs/{id}")
    @Validated
    @Operation(summary = "Get a promotion tariff by ID for Admin", description = "Retrieves a promotion tariff by its unique identifier (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the promotion tariff")
    public ResponseEntity<PromotionTariffResponseDTO> getPromotionTariffByIdForAdmin(@NotNull @PathVariable Integer id) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.getPromotionTariffById(id);

        log.info("Promotional tariff: " + id + " for Admin successfully received");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }

    @PostMapping("/admin/promotions-tariffs")
    @Operation(summary = "Create a new promotion tariff (Admin)", description = "Creates a new promotion tariff (for Admin)")
    public ResponseEntity<PromotionTariffResponseDTO> createPromotionTariff(@RequestBody CreatePromotionTariffRequestDTO createPromotionTariffRequestDTO) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.createPromotionTariff(
                createPromotionTariffRequestDTO);

        log.info("Promotional tariff: " + createPromotionTariffRequestDTO + " created successfully");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }

    @PutMapping("/admin/promotions-tariffs/{id}")
    @Validated
    @Operation(summary = "Update a promotion tariff (Admin)", description = "Updates a promotion tariff by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the promotion tariff")
    public ResponseEntity<PromotionTariffResponseDTO> updatePromotionTariff(@NotNull @PathVariable Integer id,
                                                                            @RequestBody UpdatePromotionTariffRequestDTO updatePromotionTariffRequestDTO) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.updatePromotionTariff(id,
                updatePromotionTariffRequestDTO);

        log.info("Promotional tariff: " + id + " has successfully changed");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }

    @DeleteMapping("/admin/promotions-tariffs/{id}")
    @Validated
    @Operation(summary = "Delete a promotion tariff (Admin)", description = "Deletes a promotion tariff by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the promotion tariff")
    public ResponseEntity<Void> deletePromotionTariff(@NotNull @PathVariable Integer id) {
        promotionTariffService.deletePromotionTariff(id);

        log.info("Promotional tariff: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/promotions-tariffs")
    @Operation(summary = "Get all promotion tariffs for Client", description = "Retrieves a paginated list of all promotion tariffs (for Client)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=discountPercentage,asc)")
    public ResponseEntity<Page<PromotionTariffResponseDTO>> getAllPromotionsTariffsForClient(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                                             Pageable pageable) {
        Page<PromotionTariffResponseDTO> promotionsTariffs = promotionTariffService.getAllPromotionsTariffs(pageable);

        log.info("Promotional tariff for Client successfully received");

        return ResponseEntity.ok(promotionsTariffs);
    }

    @ExecutionTime
    @GetMapping("/client/promotions-tariffs/{id}")
    @Validated
    @Operation(summary = "Get a promotion tariff by ID for Client", description = "Retrieves a promotion tariff by its unique identifier (for Client)")
    @Parameter(name = "id", description = "Unique identifier of the promotion tariff")
    public ResponseEntity<PromotionTariffResponseDTO> getPromotionTariffByIdForClient(@NotNull @PathVariable Integer id) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.getPromotionTariffById(id);

        log.info("Promotional tariff: " + id + " for Client successfully received");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }
}
