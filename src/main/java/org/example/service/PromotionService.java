package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreatePromotionRequestDTO;
import org.example.dto.requestdto.UpdatePromotionRequestDTO;
import org.example.dto.responsedto.PromotionResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.PromotionMapper;
import org.example.model.Promotion;
import org.example.repository.PromotionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    private final PromotionMapper promotionMapper;

    @Transactional(readOnly = true)
    public Promotion getPromotionEntityById(Integer id) {
        return promotionRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<PromotionResponseDTO> getAllPromotions(Pageable pageable) {
        return promotionRepository.findAll(pageable)
                .map(promotionMapper::toPromotionResponseDTO);
    }

    @Transactional(readOnly = true)
    public PromotionResponseDTO getPromotionById(Integer id) {
        return promotionRepository.findById(id)
                .map(promotionMapper::toPromotionResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException("Promotion: " + id + " not found"));
    }

    @Transactional
    public PromotionResponseDTO createPromotion(CreatePromotionRequestDTO createPromotionRequestDTO) {
        return Optional.of(createPromotionRequestDTO)
                .map(promotionMapper::toPromotionForCreate)
                .map(promotionRepository::save)
                .map(promotionMapper::toPromotionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public PromotionResponseDTO updatePromotion(Integer id, UpdatePromotionRequestDTO updatePromotionRequestDTO) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Promotion: " + id + " not found"));

        promotion.setTitle(updatePromotionRequestDTO.getTitle());
        promotion.setDescription(updatePromotionRequestDTO.getDescription());
        promotion.setDiscountPercentage(updatePromotionRequestDTO.getDiscountPercentage());
        promotion.setStartDate(updatePromotionRequestDTO.getStartDate());
        promotion.setEndDate(updatePromotionRequestDTO.getEndDate());

        return Optional.of(promotion)
                .map(promotionRepository::save)
                .map(promotionMapper::toPromotionResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deletePromotion(Integer id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Promotion: " + id + " not found"));

        promotionRepository.delete(promotion);
    }
}
