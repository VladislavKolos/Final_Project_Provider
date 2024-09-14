package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.customannotation.ExistPlanId;
import org.example.annotation.customannotation.ValidDirectionValue;
import org.example.annotation.customannotation.ValidSortByValue;
import org.example.dto.requestdto.CreatePlanRequestDTO;
import org.example.dto.requestdto.UpdatePlanRequestDTO;
import org.example.dto.responsedto.PlanResponseDTO;
import org.example.service.PlanService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlanRestController {
    private final PlanService planService;

    @GetMapping("/admin/plans")
    @Validated
    public ResponseEntity<Page<PlanResponseDTO>> getAllPlansForAdmin(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                     @RequestParam(defaultValue = "10") @Positive int size,
                                                                     @NotNull @ValidSortByValue @RequestParam(defaultValue = "name") String sortBy,
                                                                     @NotNull @ValidDirectionValue @RequestParam(defaultValue = "asc") String direction) {
        Page<PlanResponseDTO> plans = planService.getAllPlans(page, size, sortBy, direction);

        log.info("Plans for Admin successfully received");

        return ResponseEntity.ok(plans);
    }

    @GetMapping("/admin/plans/{id}")
    @Validated
    public ResponseEntity<PlanResponseDTO> getPlanByIdForAdmin(@ExistPlanId @PathVariable Integer id) {
        PlanResponseDTO planResponseDTO = planService.getPlanById(id);

        log.info("Plan for Admin: " + id + " successfully received");

        return ResponseEntity.ok(planResponseDTO);
    }

    @PostMapping("/admin/plans")
    public ResponseEntity<PlanResponseDTO> createPlan(@Valid @RequestBody CreatePlanRequestDTO createPlanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.createPlan(createPlanRequestDTO);

        log.info("Plan: " + createPlanRequestDTO + " created successfully");

        return ResponseEntity.ok(planResponseDTO);
    }

    @PutMapping("/admin/plans/{id}")
    @Validated
    public ResponseEntity<PlanResponseDTO> updatePlan(@ExistPlanId @PathVariable Integer id,
                                                      @Valid @RequestBody UpdatePlanRequestDTO updatePlanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.updatePlan(id, updatePlanRequestDTO);

        log.info("The Plan: " + id + " has successfully changed");

        return ResponseEntity.ok(planResponseDTO);
    }

    @DeleteMapping("/admin/plans/{id}")
    @Validated
    public ResponseEntity<Void> deletePlan(@ExistPlanId @PathVariable Integer id) {
        planService.deletePlan(id);

        log.info("The plan: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/plans")
    @Validated
    public ResponseEntity<Page<PlanResponseDTO>> getAllPlansForClient(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                      @RequestParam(defaultValue = "10") @Positive int size,
                                                                      @NotNull @ValidSortByValue @RequestParam(defaultValue = "name") String sortBy,
                                                                      @NotNull @ValidDirectionValue @RequestParam(defaultValue = "asc") String direction) {
        Page<PlanResponseDTO> plans = planService.getAllPlans(page, size, sortBy, direction);

        log.info("Plans for Client successfully received");

        return ResponseEntity.ok(plans);
    }

    @GetMapping("/client/plans/{id}")
    @Validated
    public ResponseEntity<PlanResponseDTO> getPlanByIdForClient(@ExistPlanId @PathVariable Integer id) {
        PlanResponseDTO planResponseDTO = planService.getPlanById(id);

        log.info("Plan for Client: " + id + " successfully received");

        return ResponseEntity.ok(planResponseDTO);
    }
}
