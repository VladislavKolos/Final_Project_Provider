package org.example.mapper;

import org.example.dto.request_dto.PromotionRequestDTO;
import org.example.dto.response_dto.PromotionResponseDTO;
import org.example.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PromotionMapper {

    PromotionResponseDTO toPromotionResponseDTO(Promotion promotion);

    @Mapping(target = "id", ignore = true)
    Promotion toPromotionForCreate(PromotionRequestDTO promotionRequestDTO);
}
