package org.example.mapper;

import org.example.dto.requestdto.PromotionRequestDTO;
import org.example.dto.responsedto.PromotionResponseDTO;
import org.example.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PromotionMapper {

    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
    PromotionResponseDTO toPromotionResponseDTO(Promotion promotion);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
    Promotion toPromotionForCreate(PromotionRequestDTO promotionRequestDTO);
}
