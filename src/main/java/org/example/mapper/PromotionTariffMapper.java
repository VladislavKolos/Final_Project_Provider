package org.example.mapper;

import org.example.dto.requestdto.CreatePromotionTariffRequestDTO;
import org.example.dto.responsedto.PromotionTariffResponseDTO;
import org.example.model.PromotionTariff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting PromotionTariff and DTO objects.
 */
@Component
@Mapper(componentModel = "spring", uses = {TariffMapper.class, PromotionMapper.class})
public interface PromotionTariffMapper {

    @Mapping(source = "promotion", target = "promotion")
    @Mapping(source = "tariff", target = "tariff")
    PromotionTariffResponseDTO toPromotionTariffResponseDTO(PromotionTariff promotionTariff);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "tariffId", target = "tariff.id")
    @Mapping(source = "promotionId", target = "promotion.id")
    PromotionTariff toPromotionTariffForCreate(CreatePromotionTariffRequestDTO promotionTariffRequestDTO);
}
