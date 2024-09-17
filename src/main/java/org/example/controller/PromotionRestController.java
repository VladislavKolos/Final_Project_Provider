package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PromotionRestController {
    private final PromotionService promotionService;

    @GetMapping("/admin/promotions")
    @Validated
    public ResponseEntity<Page<PromotionResponseDTO>> getAllPromotionsForAdmin(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                               Pageable pageable) {
        Page<PromotionResponseDTO> promotions = promotionService.getAllPromotions(pageable);

        log.info("Promotions for Admin successfully received");

        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/admin/promotions/{id}")
    @Validated
    public ResponseEntity<PromotionResponseDTO> getPromotionByIdForAdmin(@NotNull @PathVariable Integer id) {
        PromotionResponseDTO promotionResponseDTO = promotionService.getPromotionById(id);

        log.info("Promotion: " + id + " for Admin successfully received");

        return ResponseEntity.ok(promotionResponseDTO);
    }

    @PostMapping("/admin/promotions")
    public ResponseEntity<PromotionResponseDTO> createPromotion(@Valid @RequestBody CreatePromotionRequestDTO createPromotionRequestDTO) {
        PromotionResponseDTO promotionResponseDTO = promotionService.createPromotion(createPromotionRequestDTO);

        log.info("Promotion: " + createPromotionRequestDTO + " created successfully");

        return ResponseEntity.ok(promotionResponseDTO);
    }

    @PutMapping("/admin/promotions/{id}")
    @Validated
    public ResponseEntity<PromotionResponseDTO> updatePromotion(@NotNull @PathVariable Integer id,
                                                                @Valid @RequestBody UpdatePromotionRequestDTO updatePromotionRequestDTO) {
        PromotionResponseDTO promotionResponseDTO = promotionService.updatePromotion(id, updatePromotionRequestDTO);

        log.info("The promotion: " + id + " has successfully changed");

        return ResponseEntity.ok(promotionResponseDTO);
    }

    @DeleteMapping("/admin/promotions/{id}")
    @Validated
    public ResponseEntity<Void> deletePromotion(@NotNull @PathVariable Integer id) {
        promotionService.deletePromotion(id);

        log.info("The promotion: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/promotions")
    @Validated
    public ResponseEntity<Page<PromotionResponseDTO>> getAllPromotionsForClient(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                                Pageable pageable) {
        Page<PromotionResponseDTO> promotions = promotionService.getAllPromotions(pageable);

        log.info("Promotions for Client successfully received");

        return ResponseEntity.ok(promotions);
    }
}
