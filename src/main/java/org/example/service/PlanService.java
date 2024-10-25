package org.example.service;

import org.example.dto.requestdto.CreatePlanRequestDTO;
import org.example.dto.requestdto.UpdatePlanRequestDTO;
import org.example.dto.responsedto.PlanResponseDTO;
import org.example.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * This interface defines methods for CRUD (Create, Read, Update, Delete) operations on plans.
 * It also provides methods for retrieving plans by ID and paginated queries.
 * Plan data is represented by both model entities (Plan) and Data Transfer Objects (DTOs)
 * for improved data representation in different layers of the application.
 */
@Component
public interface PlanService {
    Plan getPlanEntityById(Integer id);

    Page<PlanResponseDTO> getAllPlans(Pageable pageable);

    PlanResponseDTO getPlanById(Integer id);

    PlanResponseDTO createPlan(CreatePlanRequestDTO createPlanRequestDTO);

    PlanResponseDTO updatePlan(Integer id, UpdatePlanRequestDTO updatePlanRequestDTO);

    void deletePlan(Integer id);
}
