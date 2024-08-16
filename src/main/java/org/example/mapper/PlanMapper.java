package org.example.mapper;

import org.example.dto.request_dto.PlanRequestDTO;
import org.example.dto.response_dto.PlanResponseDTO;
import org.example.model.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {TariffMapper.class})
public interface PlanMapper {

    @Mapping(source = "tariff.id", target = "tariff.id")
    PlanResponseDTO toPlanResponseDTO(Plan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "tariff.id", target = "tariff.id")
    Plan toPlanForCreate(PlanRequestDTO planRequestDTO);
}
