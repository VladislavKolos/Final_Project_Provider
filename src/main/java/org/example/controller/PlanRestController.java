package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Plans", description = "Operations for managing plans (Admin and Client)")
public class PlanRestController {
    private final PlanService planService;

    @ExecutionTime
    @GetMapping("/admin/plans")
    @Operation(summary = "Get all plans for Admin", description = "Retrieves a paginated list of all plans (for Admin)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=name,asc)")
    public ResponseEntity<Page<PlanResponseDTO>> getAllPlansForAdmin(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, value = 5)
                                                                     Pageable pageable) {
        Page<PlanResponseDTO> plans = planService.getAllPlans(pageable);

        log.info("Plans for Admin successfully received");

        return ResponseEntity.ok(plans);
    }

    @ExecutionTime
    @GetMapping("/admin/plans/{id}")
    @Validated
    @Operation(summary = "Get a plan by ID for Admin", description = "Retrieves a plan by its unique identifier (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the plan")
    public ResponseEntity<PlanResponseDTO> getPlanByIdForAdmin(@NotNull @PathVariable Integer id) {
        PlanResponseDTO planResponseDTO = planService.getPlanById(id);

        log.info("Plan: " + id + " for Admin successfully received");

        return ResponseEntity.ok(planResponseDTO);
    }

    @PostMapping("/admin/plans")
    @Operation(summary = "Create a new plan (Admin)", description = "Creates a new plan (for Admin)")
    public ResponseEntity<PlanResponseDTO> createPlan(@Valid @RequestBody CreatePlanRequestDTO createPlanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.createPlan(createPlanRequestDTO);

        log.info("Plan: " + createPlanRequestDTO + " created successfully");

        return ResponseEntity.ok(planResponseDTO);
    }

    @PutMapping("/admin/plans/{id}")
    @Validated
    @Operation(summary = "Update a plan (Admin)", description = "Updates a plan by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the plan")
    public ResponseEntity<PlanResponseDTO> updatePlan(@NotNull @PathVariable Integer id,
                                                      @Valid @RequestBody UpdatePlanRequestDTO updatePlanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.updatePlan(id, updatePlanRequestDTO);

        log.info("The plan: " + id + " has successfully changed");

        return ResponseEntity.ok(planResponseDTO);
    }

    @DeleteMapping("/admin/plans/{id}")
    @Validated
    @Operation(summary = "Delete a plan (Admin)", description = "Deletes a plan by its ID (for Admin)")
    @Parameter(name = "id", description = "Unique identifier of the plan")
    public ResponseEntity<Void> deletePlan(@NotNull @PathVariable Integer id) {
        planService.deletePlan(id);

        log.info("The plan: " + id + " has successfully deleted");

        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @GetMapping("/client/plans")
    @Operation(summary = "Get all plans for Client", description = "Retrieves a paginated list of all plans (for Client)")
    @Parameter(name = "pageable", description = "Pagination information (optional, default: page=0, size=5, sort=name,asc)")
    public ResponseEntity<Page<PlanResponseDTO>> getAllPlansForClient(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, value = 5)
                                                                      Pageable pageable) {
        Page<PlanResponseDTO> plans = planService.getAllPlans(pageable);

        log.info("Plans for Client successfully received");

        return ResponseEntity.ok(plans);
    }

    @ExecutionTime
    @GetMapping("/client/plans/{id}")
    @Validated
    @Operation(summary = "Get a plan by ID for Client", description = "Retrieves a plan by its unique identifier (for Client)")
    @Parameter(name = "id", description = "Unique identifier of the plan")
    public ResponseEntity<PlanResponseDTO> getPlanByIdForClient(@NotNull @PathVariable Integer id) {
        PlanResponseDTO planResponseDTO = planService.getPlanById(id);

        log.info("Plan: " + id + " for Client successfully received");

        return ResponseEntity.ok(planResponseDTO);
    }
}
