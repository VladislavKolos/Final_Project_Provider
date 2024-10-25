package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.CreatePromotionRequestDTO;
import org.example.dto.requestdto.UpdatePromotionRequestDTO;
import org.example.dto.responsedto.PromotionResponseDTO;
import org.example.service.PromotionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing promotions.
 * This class provides an API for managing promotions, including getting, creating, updating, and deleting.
 * It is divided into methods for administrator and client.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Promotions", description = "Operations related to managing promotions (Admin and Client)")
public class PromotionRestController {
    private final PromotionService promotionService;

    @ExecutionTime
    @GetMapping("/admin/promotions")
    @Operation(summary = "Get all promotions for Admin", description = "Retrieves a paginated list of all promotions (for Admin)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=discountPercentage,asc)")
    public ResponseEntity<Page<PromotionResponseDTO>> getAllPromotionsForAdmin(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                               Pageable pageable) {
        Page<PromotionResponseDTO> promotions = promotionService.getAllPromotions(pageable);

        log.info("Promotions for Admin successfully received");

        return ResponseEntity.ok(promotions);
    }

    @ExecutionTime
    @GetMapping("/admin/promotions/{id}")
    @Validated
    @Operation(summary = "Get a promotion by ID for Admin", description = "Retrieves a promotion by its unique identifier (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the promotion")
    public ResponseEntity<PromotionResponseDTO> getPromotionByIdForAdmin(@NotNull @PathVariable Integer id) {
        PromotionResponseDTO promotionResponseDTO = promotionService.getPromotionById(id);

        log.info("Promotion: " + id + " for Admin successfully received");

        return ResponseEntity.ok(promotionResponseDTO);
    }

    @PostMapping("/admin/promotions")
    @Operation(summary = "Create a new promotion (Admin)", description = "Creates a new promotion (for Admin)")
    public ResponseEntity<PromotionResponseDTO> createPromotion(@Valid @RequestBody CreatePromotionRequestDTO createPromotionRequestDTO) {
        PromotionResponseDTO promotionResponseDTO = promotionService.createPromotion(createPromotionRequestDTO);

        log.info("Promotion: " + createPromotionRequestDTO + " created successfully");

        return ResponseEntity.ok(promotionResponseDTO);
    }

    @PutMapping("/admin/promotions/{id}")
    @Validated
    @Operation(summary = "Update a promotion (Admin)", description = "Updates a promotion by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the promotion")
    public ResponseEntity<PromotionResponseDTO> updatePromotion(@NotNull @PathVariable Integer id,
                                                                @Valid @RequestBody UpdatePromotionRequestDTO updatePromotionRequestDTO) {
        PromotionResponseDTO promotionResponseDTO = promotionService.updatePromotion(id, updatePromotionRequestDTO);

        log.info("The promotion: " + id + " has successfully changed");

        return ResponseEntity.ok(promotionResponseDTO);
    }

    @DeleteMapping("/admin/promotions/{id}")
    @Validated
    @Operation(summary = "Delete a promotion (Admin)", description = "Deletes a promotion by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the promotion")
    public ResponseEntity<Void> deletePromotion(@NotNull @PathVariable Integer id) {
        promotionService.deletePromotion(id);

        log.info("The promotion: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/promotions")
    @Operation(summary = "Get all promotions for Client", description = "Retrieves a paginated list of all promotions (for Client)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=discountPercentage,asc)")
    public ResponseEntity<Page<PromotionResponseDTO>> getAllPromotionsForClient(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                                Pageable pageable) {
        Page<PromotionResponseDTO> promotions = promotionService.getAllPromotions(pageable);

        log.info("Promotions for Client successfully received");

        return ResponseEntity.ok(promotions);
    }

    @ExecutionTime
    @GetMapping("/client/promotions/{id}")
    @Validated
    @Operation(summary = "Get a promotion by ID for Client", description = "Retrieves a promotion by its unique identifier (for Client)")
    @Parameter(name = "id", description = "Unique identifier of the promotion")
    public ResponseEntity<PromotionResponseDTO> getPromotionByIdForClient(@NotNull @PathVariable Integer id) {
        PromotionResponseDTO promotionResponseDTO = promotionService.getPromotionById(id);

        log.info("Promotion: " + id + " for Client successfully received");

        return ResponseEntity.ok(promotionResponseDTO);
    }
}
