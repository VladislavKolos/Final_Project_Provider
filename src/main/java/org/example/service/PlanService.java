package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreatePlanRequestDTO;
import org.example.dto.requestdto.UpdatePlanRequestDTO;
import org.example.dto.responsedto.PlanResponseDTO;
import org.example.mapper.PlanMapper;
import org.example.model.Plan;
import org.example.repository.PlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    private final PlanMapper planMapper;

    private final TariffService tariffService;

    @Transactional(readOnly = true)
    public Page<PlanResponseDTO> getAllPlans(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        return planRepository.findAll(pageable)
                .map(planMapper::toPlanResponseDTO);
    }

    @Transactional(readOnly = true)
    public PlanResponseDTO getPlanById(Integer id) {
        return planRepository.findById(id)
                .map(planMapper::toPlanResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public PlanResponseDTO createPlan(CreatePlanRequestDTO createPlanRequestDTO) {
        Plan plan = Plan.builder()
                .name(createPlanRequestDTO.getName())
                .description(createPlanRequestDTO.getDescription())
                .startDate(createPlanRequestDTO.getStartDate())
                .endDate(createPlanRequestDTO.getEndDate())
                .tariff(tariffService.getTariffEntityById(createPlanRequestDTO.getTariffId()))
                .build();

        return Optional.of(plan)
                .map(planRepository::save)
                .map(planMapper::toPlanResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public PlanResponseDTO updatePlan(Integer id, UpdatePlanRequestDTO updatePlanRequestDTO) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        plan.setName(updatePlanRequestDTO.getName());
        plan.setDescription(updatePlanRequestDTO.getDescription());
        plan.setStartDate(updatePlanRequestDTO.getStartDate());
        plan.setEndDate(updatePlanRequestDTO.getEndDate());
        plan.setTariff(tariffService.getTariffEntityById(updatePlanRequestDTO.getTariffId()));

        return Optional.of(plan)
                .map(planRepository::save)
                .map(planMapper::toPlanResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deletePlan(Integer id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        planRepository.delete(plan);
    }
}
