package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistTariffId;
import org.example.annotation.customannotation.ValidDirectionValue;
import org.example.annotation.customannotation.ValidSortByValue;
import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.requestdto.UpdateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.service.TariffService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TariffRestController {
    private final TariffService tariffService;

    @GetMapping("/admin/tariffs")
    @Validated
    public ResponseEntity<Page<TariffResponseDTO>> getAllTariffsForAdmin(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                         @RequestParam(defaultValue = "10") @Positive int size,
                                                                         @NotNull @ValidSortByValue @RequestParam(defaultValue = "name") String sortBy,
                                                                         @NotNull @ValidDirectionValue @RequestParam(defaultValue = "asc") String direction) {
        Page<TariffResponseDTO> tariffs = tariffService.getAllTariffs(page, size, sortBy, direction);

        log.info("Tariffs for Admin successfully received");

        return ResponseEntity.ok(tariffs);
    }

    @GetMapping("/admin/tariffs/{id}")
    @Validated
    public ResponseEntity<TariffResponseDTO> getTariffByIdForAdmin(@NotNull @ExistTariffId @PathVariable Integer id) {
        TariffResponseDTO tariffResponseDTO = tariffService.getTariffById(id);

        log.info("Tariff for Admin: " + id + " successfully received");

        return ResponseEntity.ok(tariffResponseDTO);
    }

    @PostMapping("/admin/tariffs")
    public ResponseEntity<TariffResponseDTO> createTariff(@Valid @RequestBody CreateTariffRequestDTO createTariffRequestDTO) {
        TariffResponseDTO tariffResponseDTO = tariffService.createTariff(createTariffRequestDTO);

        log.info("Tariff: " + createTariffRequestDTO + " created successfully");

        return ResponseEntity.ok(tariffResponseDTO);
    }

    @PutMapping("/admin/tariffs/{id}")
    @Validated
    public ResponseEntity<TariffResponseDTO> updateTariff(@NotNull @ExistTariffId @PathVariable Integer id,
                                                          @Valid @RequestBody UpdateTariffRequestDTO updateTariffRequestDTO) {
        TariffResponseDTO tariffResponseDTO = tariffService.updateTariff(id, updateTariffRequestDTO);

        log.info("The tariff: " + id + " has successfully changed");

        return ResponseEntity.ok(tariffResponseDTO);
    }

    @DeleteMapping("/admin/tariffs/{id}")
    @Validated
    public ResponseEntity<Void> deleteTariff(@NotNull @ExistTariffId @PathVariable Integer id) {
        tariffService.deleteTariff(id);

        log.info("The tariff: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/tariffs")
    @Validated
    public ResponseEntity<Page<TariffResponseDTO>> getAllTariffsForClient(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                          @RequestParam(defaultValue = "10") @Positive int size,
                                                                          @NotNull @ValidSortByValue @RequestParam(defaultValue = "name") String sortBy,
                                                                          @NotNull @ValidDirectionValue @RequestParam(defaultValue = "asc") String direction) {
        Page<TariffResponseDTO> tariffs = tariffService.getAllTariffs(page, size, sortBy, direction);

        log.info("Tariffs for Client successfully received");

        return ResponseEntity.ok(tariffs);
    }

    @GetMapping("/client/tariffs/{id}")
    @Validated
    public ResponseEntity<TariffResponseDTO> getTariffByIdForClient(@ExistTariffId @PathVariable Integer id) {
        TariffResponseDTO tariffResponseDTO = tariffService.getTariffById(id);

        log.info("Tariff for Client: " + id + " successfully received");

        return ResponseEntity.ok(tariffResponseDTO);
    }
}
