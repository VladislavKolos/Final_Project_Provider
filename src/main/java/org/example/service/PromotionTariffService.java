package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreatePromotionTariffRequestDTO;
import org.example.dto.requestdto.UpdatePromotionTariffRequestDTO;
import org.example.dto.responsedto.PromotionTariffResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.PromotionTariffMapper;
import org.example.model.PromotionTariff;
import org.example.repository.PromotionTariffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionTariffService {

    private final PromotionTariffRepository promotionTariffRepository;

    private final PromotionTariffMapper promotionTariffMapper;

    private final PromotionService promotionService;

    private final TariffService tariffService;

    @Transactional(readOnly = true)
    public Page<PromotionTariffResponseDTO> getAllPromotionsTariffs(Pageable pageable) {
        return promotionTariffRepository.findAll(pageable).map(promotionTariffMapper::toPromotionTariffResponseDTO);
    }

    @Transactional(readOnly = true)
    public PromotionTariffResponseDTO getPromotionTariffById(Integer id) {
        return promotionTariffRepository.findById(id)
                .map(promotionTariffMapper::toPromotionTariffResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException("Promotional tariff: " + id + " not found"));
    }

    @Transactional
    public PromotionTariffResponseDTO createPromotionTariff(CreatePromotionTariffRequestDTO createPromotionTariffRequestDTO) {

        PromotionTariff promotionTariff = PromotionTariff.builder()
                .promotion(promotionService.getPromotionEntityById(createPromotionTariffRequestDTO.getPromotionId()))
                .tariff(tariffService.getTariffEntityById(createPromotionTariffRequestDTO.getTariffId()))
                .build();

        return Optional.of(promotionTariff)
                .map(promotionTariffRepository::save)
                .map(promotionTariffMapper::toPromotionTariffResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public PromotionTariffResponseDTO updatePromotionTariff(Integer id,
                                                            UpdatePromotionTariffRequestDTO updatePromotionTariffRequestDTO) {

        PromotionTariff promotionTariff = promotionTariffRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Promotional tariff: " + id + " not found"));

        promotionTariff.setPromotion(promotionService.getPromotionEntityById(updatePromotionTariffRequestDTO.getPromotionId()));
        promotionTariff.setTariff(tariffService.getTariffEntityById(updatePromotionTariffRequestDTO.getTariffId()));

        return Optional.of(promotionTariff)
                .map(promotionTariffRepository::save)
                .map(promotionTariffMapper::toPromotionTariffResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deletePromotionTariff(Integer id) {
        PromotionTariff promotionTariff = promotionTariffRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Promotional tariff: " + id + " not found"));

        promotionTariffRepository.delete(promotionTariff);
    }

}
