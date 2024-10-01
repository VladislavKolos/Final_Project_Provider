package org.example.service;

import org.example.dto.requestdto.CreatePromotionTariffRequestDTO;
import org.example.dto.requestdto.UpdatePromotionTariffRequestDTO;
import org.example.dto.responsedto.PromotionTariffResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * This interface defines methods for CRUD (Create, Read, Update, Delete) operations on promotion tariffs.
 * It also provides methods for retrieving promotion tariffs by ID and paginated queries.
 * Promotion tariff data is likely represented by a dedicated model entity and Data Transfer Objects (DTOs)
 * for improved data representation in different application layers.
 */
@Component
public interface PromotionTariffService {
    Page<PromotionTariffResponseDTO> getAllPromotionsTariffs(Pageable pageable);

    PromotionTariffResponseDTO getPromotionTariffById(Integer id);

    PromotionTariffResponseDTO createPromotionTariff(CreatePromotionTariffRequestDTO createPromotionTariffRequestDTO);

    PromotionTariffResponseDTO updatePromotionTariff(Integer id,
                                                     UpdatePromotionTariffRequestDTO updatePromotionTariffRequestDTO);

    void deletePromotionTariff(Integer id);
}
