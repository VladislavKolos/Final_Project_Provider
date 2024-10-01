package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.CreateTariffRequestDTO;
import org.example.dto.requestdto.UpdateTariffRequestDTO;
import org.example.dto.responsedto.TariffResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.TariffMapper;
import org.example.model.Tariff;
import org.example.repository.TariffRepository;
import org.example.service.TariffService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for working with tariffs.
 * This service provides methods for obtaining, creating, updating and deleting tariffs.
 * It interacts with the tariff repository (`TariffRepository`), mapper (`TariffMapper`) to transform objects.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    private final MessageSource messageSource;

    /**
     * This method fetches a tariff entity from the database using the provided ID.
     *
     * @param id Tariff ID
     * @return The tariff entity, or throws an exception if not found.
     */
    @Transactional(readOnly = true)
    public Tariff getTariffEntityById(Integer id) {
        return tariffRepository.findById(id).orElseThrow();
    }

    /**
     * This method fetches all tariffs from the database, applying the specified pagination parameters.
     * The results are then mapped to `TariffResponseDTO` objects for response.
     *
     * @param pageable The pagination parameters.
     * @return A page of `TariffResponseDTO` objects representing all tariffs.
     */
    @Transactional(readOnly = true)
    public Page<TariffResponseDTO> getAllTariffs(Pageable pageable) {
        return tariffRepository.findAll(pageable)
                .map(tariffMapper::toTariffResponseDTO);
    }

    /**
     * This method fetches a tariff entity from the database using the provided ID and maps it to a
     * `TariffResponseDTO`. If the tariff is not found, a `ProviderNotFoundException` is thrown.
     *
     * @param id Tariff ID
     * @return The tariff as a response DTO.
     */
    @Transactional(readOnly = true)
    public TariffResponseDTO getTariffById(Integer id) {
        return tariffRepository.findById(id)
                .map(tariffMapper::toTariffResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "tariff.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method constructs a new `Tariff` entity from the provided `CreateTariffRequestDTO`,
     * persists it to the database, and returns a `TariffResponseDTO` representing the created tariff.
     *
     * @param tariffRequestDTO The DTO containing the information for the new tariff.
     * @return The created tariff as a response DTO.
     */
    @Transactional
    public TariffResponseDTO createTariff(CreateTariffRequestDTO tariffRequestDTO) {
        return Optional.of(tariffRequestDTO)
                .map(tariffMapper::toTariffForCreate)
                .map(tariffRepository::save)
                .map(tariffMapper::toTariffResponseDTO)
                .orElseThrow();
    }

    /**
     * This method retrieves an existing tariff by its ID, updates its properties based on the
     * provided `UpdateTariffRequestDTO`, and persists the updated tariff.
     *
     * @param id                     Tariff ID
     * @param updateTariffRequestDTO The DTO containing the updated tariff information.
     * @return The updated tariff as a response DTO.
     */
    @Transactional
    public TariffResponseDTO updateTariff(Integer id, UpdateTariffRequestDTO updateTariffRequestDTO) {
        Tariff tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "tariff.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        setTariff(tariff, updateTariffRequestDTO);

        return Optional.of(tariff)
                .map(tariffRepository::save)
                .map(tariffMapper::toTariffResponseDTO)
                .orElseThrow();
    }

    /**
     * This method deletes the tariff with the specified ID from the database.
     *
     * @param id Tariff ID
     */
    @Transactional
    public void deleteTariff(Integer id) {
        Tariff tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "tariff.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        tariffRepository.delete(tariff);
    }

    /**
     * This private helper method sets the properties of the given `Tariff` entity based on the
     * provided `UpdateTariffRequestDTO`.
     *
     * @param tariff                 The tariff entity to be updated.
     * @param updateTariffRequestDTO The DTO containing the updated tariff information.
     */
    private void setTariff(Tariff tariff, UpdateTariffRequestDTO updateTariffRequestDTO) {
        tariff.setName(updateTariffRequestDTO.getName());
        tariff.setDescription(updateTariffRequestDTO.getDescription());
        tariff.setMonthlyCost(updateTariffRequestDTO.getMonthlyCost());
        tariff.setDataLimit(updateTariffRequestDTO.getDataLimit());
        tariff.setVoiceLimit(updateTariffRequestDTO.getVoiceLimit());
    }
}
