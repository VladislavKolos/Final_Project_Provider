package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreatePromotionTariffRequestDTO;
import org.example.dto.requestdto.UpdatePromotionTariffRequestDTO;
import org.example.dto.responsedto.PromotionTariffResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.PromotionTariffMapper;
import org.example.model.PromotionTariff;
import org.example.repository.PromotionTariffRepository;
import org.example.service.PromotionService;
import org.example.service.PromotionTariffService;
import org.example.service.TariffService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for working with promotional tariffs.
 * This service provides methods for obtaining, creating, updating and deleting promotional rates.
 * It interacts with the `PromotionTariffRepository` repository, mapper(`PromotionTariffMapper`) for transforming objects,
 * as well as with the `PromotionService` and `TariffService` services.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionTariffServiceImpl implements PromotionTariffService {

    private final PromotionTariffRepository promotionTariffRepository;

    private final PromotionTariffMapper promotionTariffMapper;

    private final PromotionService promotionService;

    private final TariffService tariffService;

    private final MessageSource messageSource;

    /**
     * This method searches for all promotional tariffs in the `promotionTariffRepository`, taking into account the pagination parameters.
     * The results are then converted into `PromotionTariffResponseDTO` objects using the `promotionTariffMapper` mapper.
     *
     * @param pageable Spring Data pagination option.
     * @return Page of `PromotionTariffResponseDTO` objects with information about promotional tariffs.
     */
    @Transactional(readOnly = true)
    public Page<PromotionTariffResponseDTO> getAllPromotionsTariffs(Pageable pageable) {
        return promotionTariffRepository.findAll(pageable).map(promotionTariffMapper::toPromotionTariffResponseDTO);
    }

    /**
     * This method searches for a promotional tariff in the `promotionTariffRepository` using the specified identifier.
     * If a promotional tariff is found, it is converted to a `PromotionTariffResponseDTO` object using the `promotionTariffMapper` mapper and returned.
     * If a promotional rate is not found, a `ProviderNotFoundException` exception is thrown.
     *
     * @param id PromotionTariff ID
     * @return A `PromotionTariffResponseDTO` object with information about the promotional tariff, if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public PromotionTariffResponseDTO getPromotionTariffById(Integer id) {
        return promotionTariffRepository.findById(id)
                .map(promotionTariffMapper::toPromotionTariffResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "promotion.error.tariff_not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method creates a new promotional tariff based on the data from `CreatePromotionTariffRequestDTO`,
     * saves it in the database and returns a `PromotionTariffResponseDTO` object with information about the created promotional tariff.
     *
     * @param createPromotionTariffRequestDTO DTO object `CreatePromotionTariffRequestDTO` containing information about the promotional tariff being created.
     * @return `PromotionTariffResponseDTO` object with information about the created promotional tariff.
     */
    @Transactional
    public PromotionTariffResponseDTO createPromotionTariff(CreatePromotionTariffRequestDTO createPromotionTariffRequestDTO) {

        PromotionTariff promotionTariff = buildPromotionTariff(createPromotionTariffRequestDTO);

        return Optional.of(promotionTariff)
                .map(promotionTariffRepository::save)
                .map(promotionTariffMapper::toPromotionTariffResponseDTO)
                .orElseThrow();
    }

    /**
     * This method updates an existing promotional rate with the specified ID.
     * It first finds the promotional tariff in the repository,
     * then updates its fields using data from `UpdatePromotionTariffRequestDTO` and stores the updated promotional tariff in the database.
     *
     * @param id                              PromotionTariff ID
     * @param updatePromotionTariffRequestDTO DTO object `UpdatePromotionTariffRequestDTO` containing information for updating the promotional tariff
     * @return `PromotionTariffResponseDTO` object with information about the updated promotional tariff.
     */
    @Transactional
    public PromotionTariffResponseDTO updatePromotionTariff(Integer id,
                                                            UpdatePromotionTariffRequestDTO updatePromotionTariffRequestDTO) {

        PromotionTariff promotionTariff = promotionTariffRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "promotion.error.tariff_not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        setPromotionTariff(promotionTariff, updatePromotionTariffRequestDTO);

        return Optional.of(promotionTariff)
                .map(promotionTariffRepository::save)
                .map(promotionTariffMapper::toPromotionTariffResponseDTO)
                .orElseThrow();
    }

    /**
     * This method removes the promotional tariff with the specified ID from the database.
     *
     * @param id PromotionTariff ID
     */
    @Transactional
    public void deletePromotionTariff(Integer id) {
        PromotionTariff promotionTariff = promotionTariffRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "promotion.error.tariff_not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        promotionTariffRepository.delete(promotionTariff);
    }

    /**
     * This method creates a new `PromotionTariff` object and fills its fields with data from `CreatePromotionTariffRequestDTO`.
     * It also uses the `promotionService` and `tariffService` services to obtain the `Promotion` and `Tariff` entities based on the specified identifiers.
     *
     * @param createPromotionTariffRequestDTO DTO object `CreatePromotionTariffRequestDTO` containing information about the promotional tariff being created.
     * @return The created `PromotionTariff` object.
     */
    private PromotionTariff buildPromotionTariff(CreatePromotionTariffRequestDTO createPromotionTariffRequestDTO) {
        return PromotionTariff.builder()
                .promotion(promotionService.getPromotionEntityById(createPromotionTariffRequestDTO.getPromotionId()))
                .tariff(tariffService.getTariffEntityById(createPromotionTariffRequestDTO.getTariffId()))
                .build();
    }

    /**
     * This method updates the fields of an existing `PromotionTariff` object with data from `UpdatePromotionTariffRequestDTO`.
     * It also uses the `promotionService` and `tariffService` services to obtain the `Promotion` and `Tariff` entities based on the specified identifiers.
     *
     * @param promotionTariff                 The `PromotionTariff` object that needs to be updated.
     * @param updatePromotionTariffRequestDTO DTO object `UpdatePromotionTariffRequestDTO` containing information for updating the promotional tariff.
     */
    private void setPromotionTariff(PromotionTariff promotionTariff,
                                    UpdatePromotionTariffRequestDTO updatePromotionTariffRequestDTO) {

        promotionTariff.setPromotion(promotionService.getPromotionEntityById(updatePromotionTariffRequestDTO.getPromotionId()));
        promotionTariff.setTariff(tariffService.getTariffEntityById(updatePromotionTariffRequestDTO.getTariffId()));
    }

}
