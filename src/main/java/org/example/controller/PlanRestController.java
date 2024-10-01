package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.ExecutionTime;
import org.example.dto.requestdto.CreatePlanRequestDTO;
import org.example.dto.requestdto.UpdatePlanRequestDTO;
import org.example.dto.responsedto.PlanResponseDTO;
import org.example.service.PlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing plans.
 * This class provides an API for managing plans, including getting, creating, updating, and deleting.
 * It is divided into methods for administrator and client.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlanRestController {
    private final PlanService planService;

    @ExecutionTime
    @GetMapping("/admin/plans")
    @Validated
    public ResponseEntity<Page<PlanResponseDTO>> getAllPlansForAdmin(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, value = 5)
                                                                     Pageable pageable) {
        Page<PlanResponseDTO> plans = planService.getAllPlans(pageable);

        log.info("Plans for Admin successfully received");

        return ResponseEntity.ok(plans);
    }

    @ExecutionTime
    @GetMapping("/admin/plans/{id}")
    @Validated
    public ResponseEntity<PlanResponseDTO> getPlanByIdForAdmin(@NotNull @PathVariable Integer id) {
        PlanResponseDTO planResponseDTO = planService.getPlanById(id);

        log.info("Plan: " + id + " for Admin successfully received");

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
    public ResponseEntity<PlanResponseDTO> updatePlan(@NotNull @PathVariable Integer id,
                                                      @Valid @RequestBody UpdatePlanRequestDTO updatePlanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.updatePlan(id, updatePlanRequestDTO);

        log.info("The plan: " + id + " has successfully changed");

        return ResponseEntity.ok(planResponseDTO);
    }

    @DeleteMapping("/admin/plans/{id}")
    @Validated
    public ResponseEntity<Void> deletePlan(@NotNull @PathVariable Integer id) {
        planService.deletePlan(id);

        log.info("The plan: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/plans")
    @Validated
    public ResponseEntity<Page<PlanResponseDTO>> getAllPlansForClient(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, value = 5)
                                                                      Pageable pageable) {
        Page<PlanResponseDTO> plans = planService.getAllPlans(pageable);

        log.info("Plans for Client successfully received");

        return ResponseEntity.ok(plans);
    }
}
