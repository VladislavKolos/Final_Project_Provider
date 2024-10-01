package org.example.mapper;

import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.model.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting Tariff and DTO objects.
 */
@Component
@Mapper(componentModel = "spring")
public interface TariffMapper {
    TariffResponseDTO toTariffResponseDTO(Tariff tariff);

    @Mapping(target = "id", ignore = true)
    Tariff toTariffForCreate(CreateTariffRequestDTO tariffRequestDTO);
}
