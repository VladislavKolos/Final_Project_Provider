package org.example.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PromotionTariffRestController {

    private final PromotionTariffService promotionTariffService;

    @GetMapping("/admin/promotions-tariffs")
    @Validated
    public ResponseEntity<Page<PromotionTariffResponseDTO>> getAllPromotionsTariffsForAdmin(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                                            Pageable pageable) {
        Page<PromotionTariffResponseDTO> promotionsTariffs = promotionTariffService.getAllPromotionsTariffs(pageable);

        log.info("Promotional tariff for Admin successfully received");

        return ResponseEntity.ok(promotionsTariffs);
    }

    @GetMapping("/admin/promotions-tariffs/{id}")
    @Validated
    public ResponseEntity<PromotionTariffResponseDTO> getPromotionTariffByIdForAdmin(@NotNull @PathVariable Integer id) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.getPromotionTariffById(id);

        log.info("Promotional tariff: " + id + " for Admin successfully received");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }

    @PostMapping("/admin/promotions-tariffs")
    public ResponseEntity<PromotionTariffResponseDTO> createPromotionTariff(@RequestBody CreatePromotionTariffRequestDTO createPromotionTariffRequestDTO) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.createPromotionTariff(
                createPromotionTariffRequestDTO);

        log.info("Promotional tariff: " + createPromotionTariffRequestDTO + " created successfully");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }

    @PutMapping("/admin/promotions-tariffs/{id}")
    @Validated
    public ResponseEntity<PromotionTariffResponseDTO> updatePromotionTariff(@NotNull @PathVariable Integer id,
                                                                            @RequestBody UpdatePromotionTariffRequestDTO updatePromotionTariffRequestDTO) {
        PromotionTariffResponseDTO promotionTariffResponseDTO = promotionTariffService.updatePromotionTariff(id,
                updatePromotionTariffRequestDTO);

        log.info("Promotional tariff: " + id + " has successfully changed");

        return ResponseEntity.ok(promotionTariffResponseDTO);
    }

    @DeleteMapping("/admin/promotions-tariffs/{id}")
    @Validated
    public ResponseEntity<Void> deletePromotionTariff(@NotNull @PathVariable Integer id) {
        promotionTariffService.deletePromotionTariff(id);

        log.info("Promotional tariff: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/promotions-tariffs")
    @Validated
    public ResponseEntity<Page<PromotionTariffResponseDTO>> getAllPromotionsTariffsForClient(@PageableDefault(sort = "discountPercentage", direction = Sort.Direction.ASC, value = 5)
                                                                                             Pageable pageable) {
        Page<PromotionTariffResponseDTO> promotionsTariffs = promotionTariffService.getAllPromotionsTariffs(pageable);

        log.info("Promotional tariff for Client successfully received");

        return ResponseEntity.ok(promotionsTariffs);
    }
}
