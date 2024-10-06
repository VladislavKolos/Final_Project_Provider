package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreatePromotionRequestDTO;
import org.example.dto.requestdto.UpdatePromotionRequestDTO;
import org.example.dto.responsedto.PromotionResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.PromotionMapper;
import org.example.model.Promotion;
import org.example.repository.PromotionRepository;
import org.example.service.PromotionService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for working with promotions.
 * This service provides methods to retrieve, create, update, and delete shares.
 * It interacts with the promotion repository (`PromotionRepository`),
 * mapper (`PromotionMapper`) to transform plan objects.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    private final PromotionMapper promotionMapper;

    private final MessageSource messageSource;

    /**
     * This method looks up the promotion entity in the `promotionRepository` by the specified ID.
     * If the entity is found, it is returned. If the entity is not found, an `EntityNotFoundException` exception is thrown.
     *
     * @param id Promotion ID
     * @return The promotion entity, if found, otherwise an exception is thrown.
     */
    @Override
    @Transactional(readOnly = true)
    public Promotion getPromotionEntityById(Integer id) {
        return promotionRepository.findById(id).orElseThrow();
    }

    /**
     * This method searches for all promotions in the `promotionRepository`, taking into account the pagination parameters.
     * The results are then converted into `PromotionResponseDTO` objects using the `promotionMapper` mapper.
     *
     * @param pageable Spring Data pagination option.
     * @return Page of `PromotionResponseDTO` objects with information about promotions.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PromotionResponseDTO> getAllPromotions(Pageable pageable) {
        return promotionRepository.findAll(pageable)
                .map(promotionMapper::toPromotionResponseDTO);
    }

    /**
     * This method retrieves the promotion entity by the specified ID from the database and converts it into a `PromotionResponseDTO` object,
     * which is more convenient to pass in a response.
     * If the promotion is not found, a `ProviderNotFoundException` is thrown.
     *
     * @param id Promotion ID
     * @return A `PromotionResponseDTO` object containing information about the promotion, or a `ProviderNotFoundException` if the promotion was not found.
     */
    @Override
    @Transactional(readOnly = true)
    public PromotionResponseDTO getPromotionById(Integer id) {
        return promotionRepository.findById(id)
                .map(promotionMapper::toPromotionResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "promotion.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method creates a new promotion based on the data in `CreatePromotionRequestDTO`,
     * stores it in the database and returns a `PromotionResponseDTO` object with information about the created promotion.
     *
     * @param createPromotionRequestDTO object `CreatePromotionRequestDTO` containing information about the promotion being created.
     * @return A `PromotionResponseDTO` object with information about the created promotion.
     */
    @Override
    @Transactional
    public PromotionResponseDTO createPromotion(CreatePromotionRequestDTO createPromotionRequestDTO) {
        return Optional.of(createPromotionRequestDTO)
                .map(promotionMapper::toPromotionForCreate)
                .map(promotionRepository::save)
                .map(promotionMapper::toPromotionResponseDTO)
                .orElseThrow();
    }

    /**
     * This method updates an existing promotion with the specified ID.
     * It first finds the promotion in the repository, then updates its fields with data from `UpdatePromotionRequestDTO`
     * and stores the updated promotion in the database.
     *
     * @param id                        Promotion ID
     * @param updatePromotionRequestDTO an `UpdatePromotionRequestDTO` object containing information for updating the promotion.
     * @return A `PromotionResponseDTO` object with information about the updated promotion.
     */
    @Override
    @Transactional
    public PromotionResponseDTO updatePromotion(Integer id, UpdatePromotionRequestDTO updatePromotionRequestDTO) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "promotion.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        setPromotion(promotion, updatePromotionRequestDTO);

        return Optional.of(promotion)
                .map(promotionRepository::save)
                .map(promotionMapper::toPromotionResponseDTO)
                .orElseThrow();
    }

    /**
     * This method removes the promotion with the specified ID from the database.
     *
     * @param id Promotion ID
     */
    @Override
    @Transactional
    public void deletePromotion(Integer id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "promotion.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        promotionRepository.delete(promotion);
    }

    /**
     * This method updates the fields of an existing `Promotion` object with data from the `UpdatePromotionRequestDTO`.
     *
     * @param promotion                 The `Promotion` object to update.
     * @param updatePromotionRequestDTO an `UpdatePromotionRequestDTO` object containing information for updating the promotion.
     */
    private void setPromotion(Promotion promotion, UpdatePromotionRequestDTO updatePromotionRequestDTO) {
        promotion.setTitle(updatePromotionRequestDTO.getTitle());
        promotion.setDescription(updatePromotionRequestDTO.getDescription());
        promotion.setDiscountPercentage(updatePromotionRequestDTO.getDiscountPercentage());
        promotion.setStartDate(updatePromotionRequestDTO.getStartDate());
        promotion.setEndDate(updatePromotionRequestDTO.getEndDate());
    }
}
