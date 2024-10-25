package org.example.service;

import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.requestdto.UpdateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.model.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * This interface defines methods for CRUD (Create, Read, Update, Delete) operations on tariffs.
 * It also provides methods for retrieving tariffs by ID and paginated queries.
 * Tariff data is likely represented by a dedicated model entity (Tariff) and Data Transfer Objects (DTOs)
 * for improved data representation in different application layers.
 */
@Component
public interface TariffService {
    Tariff getTariffEntityById(Integer id);

    Page<TariffResponseDTO> getAllTariffs(Pageable pageable);

    TariffResponseDTO getTariffById(Integer id);

    TariffResponseDTO createTariff(CreateTariffRequestDTO tariffRequestDTO);

    TariffResponseDTO updateTariff(Integer id, UpdateTariffRequestDTO updateTariffRequestDTO);

    void deleteTariff(Integer id);
}
