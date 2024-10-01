package org.example.service;

import org.example.dto.requestdto.CreatePromotionRequestDTO;
import org.example.dto.requestdto.UpdatePromotionRequestDTO;
import org.example.dto.responsedto.PromotionResponseDTO;
import org.example.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * This interface defines methods for CRUD (Create, Read, Update, Delete) operations on promotions.
 * It also provides methods for retrieving promotions by ID and paginated queries.
 * Promotion data is represented by both model entities (Promotion) and Data Transfer Objects (DTOs)
 * for improved separation between data access and presentation layers.
 */
@Component
public interface PromotionService {
    Promotion getPromotionEntityById(Integer id);

    Page<PromotionResponseDTO> getAllPromotions(Pageable pageable);

    PromotionResponseDTO getPromotionById(Integer id);

    PromotionResponseDTO createPromotion(CreatePromotionRequestDTO createPromotionRequestDTO);

    PromotionResponseDTO updatePromotion(Integer id, UpdatePromotionRequestDTO updatePromotionRequestDTO);

    void deletePromotion(Integer id);
}
