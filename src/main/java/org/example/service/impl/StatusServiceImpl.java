package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.StatusRequestDTO;
import org.example.dto.responsedto.StatusResponseDTO;
import org.example.exception.ProviderNotFoundException;
import org.example.mapper.StatusMapper;
import org.example.model.Status;
import org.example.repository.StatusRepository;
import org.example.service.StatusService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with statuses.
 * This service provides methods for retrieving, creating, updating and deleting statuses.
 * It interacts with the status repository (`StatusRepository`), mapper (`StatusMapper`) to transform objects.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    private final MessageSource messageSource;

    /**
     * This method searches for a status entity in the `statusRepository` repository using the specified identifier.
     * If the entity is found, it is returned. If the entity is not found, an `EntityNotFoundException` exception is thrown.
     *
     * @param id Status ID
     * @return The status entity, if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public Status getStatusEntityById(Integer id) {
        return statusRepository.findById(id).orElseThrow();
    }

    /**
     * This method searches for all statuses in the `statusRepository`
     * and returns them as a list of `StatusResponseDTO` objects, mapped using the `statusMapper` mapper.
     *
     * @return A list of `StatusResponseDTO` objects with information about all statuses.
     */
    @Transactional(readOnly = true)
    public List<StatusResponseDTO> getAllStatuses() {
        return statusRepository.findAll()
                .stream()
                .map(statusMapper::toStatusResponseDTO)
                .toList();
    }

    /**
     * This method searches the `statusRepository` for status using the specified ID.
     * If the status is found, it is converted to a `StatusResponseDTO` object using the `statusMapper` mapper and returned.
     * If the status is not found, a `ProviderNotFoundException` is thrown.
     *
     * @param id Status ID
     * @return A `StatusResponseDTO` object with status information if found, otherwise an exception is thrown.
     */
    @Transactional(readOnly = true)
    public StatusResponseDTO getStatusById(Integer id) {
        return statusRepository.findById(id)
                .map(statusMapper::toStatusResponseDTO)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "status.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));
    }

    /**
     * This method takes a `StatusRequestDTO` as input, maps it to a `Status` entity,
     * saves the entity to the database, and then maps the saved entity back to a
     * `StatusResponseDTO` before returning it.
     *
     * @param statusRequestDTO The DTO containing the information for the new status.
     * @return The newly created status as a response DTO.
     */
    @Transactional
    public StatusResponseDTO createStatus(StatusRequestDTO statusRequestDTO) {
        return Optional.of(statusRequestDTO)
                .map(statusMapper::toStatusForCreate)
                .map(statusRepository::save)
                .map(statusMapper::toStatusResponseDTO)
                .orElseThrow();
    }

    /**
     * This method finds the status with the given ID, updates its name based on the provided
     * `StatusRequestDTO`, saves the updated entity, and returns the updated status as a DTO.
     *
     * @param id               Status ID
     * @param statusRequestDTO The DTO containing the updated status information.
     * @return The updated status as a response DTO.
     */
    @Transactional
    public StatusResponseDTO updateStatus(Integer id, StatusRequestDTO statusRequestDTO) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "status.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        status.setName(statusRequestDTO.getName());

        return Optional.of(status)
                .map(statusRepository::save)
                .map(statusMapper::toStatusResponseDTO)
                .orElseThrow();
    }

    /**
     * This method finds the status with the given ID and deletes it from the database.
     *
     * @param id Status ID
     */
    @Transactional
    public void deleteStatus(Integer id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(messageSource.getMessage(
                        "status.error.not_found.by_id",
                        new Object[]{id},
                        LocaleContextHolder.getLocale())));

        statusRepository.delete(status);
    }

    /**
     * This method searches the database for a status with the given name.
     *
     * @param name The name of the status to find.
     * @return An optional containing the status if found, or empty if not found.
     */
    @Transactional(readOnly = true)
    public Optional<Status> getStatusByName(String name) {
        return statusRepository.findByName(name);
    }
}
