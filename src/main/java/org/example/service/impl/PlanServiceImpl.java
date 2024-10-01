package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreatePlanRequestDTO;
import org.example.dto.requestdto.UpdatePlanRequestDTO;
import org.example.dto.responsedto.PlanResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.PlanMapper;
import org.example.model.Plan;
import org.example.repository.PlanRepository;
import org.example.service.PlanService;
import org.example.service.TariffService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for working with plans.
 * This service provides methods for retrieving, creating, updating, and deleting plans.
 * It interacts with the plan repository (`PlanRepository`), the mapper (`PlanMapper`) for transforming objects,
 * and also with the tariff service (`TariffService`).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;

    private final PlanMapper planMapper;

    private final TariffService tariffService;

    private final MessageSource messageSource;

    /**
     * This method searches for a plan entity in the `planRepository` repository using the specified identifier.
     * If the entity is found, it is returned. If the entity is not found, an `EntityNotFoundException` exception is thrown.
     *
     * @param id Plan ID.
     * @return The plan entity, if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public Plan getPlanEntityById(Integer id) {
        return planRepository.findById(id).orElseThrow();
    }

    /**
     * This method searches all plans in the `planRepository` repository, taking into account the pagination settings.
     * The results are then converted into `PlanResponseDTO` objects using the `planMapper` mapper.
     *
     * @param pageable Spring Data pagination option.
     * @return Page of `PlanResponseDTO` objects with information about plans.
     */
    @Transactional(readOnly = true)
    public Page<PlanResponseDTO> getAllPlans(Pageable pageable) {
        return planRepository.findAll(pageable)
                .map(planMapper::toPlanResponseDTO);
    }

    /**
     * This method searches the `planRepository` for a plan using the specified identifier.
     * If a plan is found, it is converted to a `PlanResponseDTO` object using the `planMapper` mapper and returned.
     * If the plan is not found, a `ProviderNotFoundException` is thrown.
     *
     * @param id Plan ID
     * @return A `PlanResponseDTO` object with plan information, if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public PlanResponseDTO getPlanById(Integer id) {
        return planRepository.findById(id)
                .map(planMapper::toPlanResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("plan.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method creates a new `Plan` object based on the data from `CreatePlanRequestDTO`,
     * stores it in the repository and returns the corresponding `PlanResponseDTO`.
     *
     * @param createPlanRequestDTO object `CreatePlanRequestDTO` containing information about the plan being created.
     * @return A `PlanResponseDTO` object with information about the created plan.
     */
    @Transactional
    public PlanResponseDTO createPlan(CreatePlanRequestDTO createPlanRequestDTO) {
        Plan plan = buildPlan(createPlanRequestDTO);

        return Optional.of(plan)
                .map(planRepository::save)
                .map(planMapper::toPlanResponseDTO)
                .orElseThrow();
    }

    /**
     * This method updates an existing plan with the specified ID.
     * It first finds the plan in the repository, then updates its fields with data from `UpdatePlanRequestDTO` and saves the updated plan to the database.
     *
     * @param id                   Plan ID
     * @param updatePlanRequestDTO an `UpdatePlanRequestDTO` object containing information for updating the plan.
     * @return A `PlanResponseDTO` object with information about the updated plan.
     */
    @Transactional
    public PlanResponseDTO updatePlan(Integer id, UpdatePlanRequestDTO updatePlanRequestDTO) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("plan.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        setPlan(plan, updatePlanRequestDTO);

        return Optional.of(plan)
                .map(planRepository::save)
                .map(planMapper::toPlanResponseDTO)
                .orElseThrow();
    }

    /**
     * This method removes the plan with the specified ID from the database.
     *
     * @param id Plan ID
     */
    @Transactional
    public void deletePlan(Integer id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage("plan.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        planRepository.delete(plan);
    }

    /**
     * This method creates a new `Plan` object and populates its fields with data from `CreatePlanRequestDTO`.
     * It also uses the `tariffService` service to retrieve the `Tariff` entity by the specified identifier.
     *
     * @param createPlanRequestDTO object `CreatePlanRequestDTO` containing information about the plan being created.
     * @return Created `Plan` object.
     */
    private Plan buildPlan(CreatePlanRequestDTO createPlanRequestDTO) {
        return Plan.builder()
                .name(createPlanRequestDTO.getName())
                .description(createPlanRequestDTO.getDescription())
                .startDate(createPlanRequestDTO.getStartDate())
                .endDate(createPlanRequestDTO.getEndDate())
                .tariff(tariffService.getTariffEntityById(createPlanRequestDTO.getTariffId()))
                .build();
    }

    /**
     * This method updates the fields of an existing `Plan` object with data from `UpdatePlanRequestDTO`.
     * It also uses the `tariffService` service to retrieve the `Tariff` entity by the specified identifier.
     *
     * @param plan                 The `Plan` object to update.
     * @param updatePlanRequestDTO an `UpdatePlanRequestDTO` object containing information for updating the plan.
     */
    private void setPlan(Plan plan, UpdatePlanRequestDTO updatePlanRequestDTO) {
        plan.setName(updatePlanRequestDTO.getName());
        plan.setDescription(updatePlanRequestDTO.getDescription());
        plan.setStartDate(updatePlanRequestDTO.getStartDate());
        plan.setEndDate(updatePlanRequestDTO.getEndDate());
        plan.setTariff(tariffService.getTariffEntityById(updatePlanRequestDTO.getTariffId()));
    }
}
