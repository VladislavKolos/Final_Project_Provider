package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.requestdto.UpdateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.mapper.TariffMapper;
import org.example.model.Tariff;
import org.example.repository.TariffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    @Transactional(readOnly = true)
    public Tariff getTariffEntityById(Integer id) {
        return tariffRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<TariffResponseDTO> getAllTariffs(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        return tariffRepository.findAll(pageable)
                .map(tariffMapper::toTariffResponseDTO);
    }

    @Transactional(readOnly = true)
    public TariffResponseDTO getTariffById(Integer id) {
        return tariffRepository.findById(id)
                .map(tariffMapper::toTariffResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public TariffResponseDTO createTariff(CreateTariffRequestDTO tariffRequestDTO) {
        return Optional.of(tariffRequestDTO)
                .map(tariffMapper::toTariffForCreate)
                .map(tariffRepository::save)
                .map(tariffMapper::toTariffResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public TariffResponseDTO updateTariff(Integer id, UpdateTariffRequestDTO updateTariffRequestDTO) {
        Tariff tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tariff not found"));

        tariff.setName(updateTariffRequestDTO.getName());
        tariff.setDescription(updateTariffRequestDTO.getDescription());
        tariff.setMonthlyCost(updateTariffRequestDTO.getMonthlyCost());
        tariff.setDataLimit(updateTariffRequestDTO.getDataLimit());
        tariff.setVoiceLimit(updateTariffRequestDTO.getVoiceLimit());

        return Optional.of(tariff)
                .map(tariffRepository::save)
                .map(tariffMapper::toTariffResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deleteTariff(Integer id) {
        Tariff tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tariff not found"));

        tariffRepository.delete(tariff);
    }
}
