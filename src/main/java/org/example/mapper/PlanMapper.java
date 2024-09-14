package org.example.mapper;

import org.example.dto.requestdto.CreatePlanRequestDTO;
import org.example.dto.responsedto.PlanResponseDTO;
import org.example.model.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {TariffMapper.class})
public interface PlanMapper {

    @Mapping(source = "tariff", target = "tariff")
    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
    PlanResponseDTO toPlanResponseDTO(Plan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "tariffId", target = "tariff.id")
    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
    Plan toPlanForCreate(CreatePlanRequestDTO planRequestDTO);
}
