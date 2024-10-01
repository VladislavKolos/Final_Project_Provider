package org.example.service;

import org.example.dto.requestdto.StatusRequestDTO;
import org.example.dto.responsedto.StatusResponseDTO;
import org.example.model.Status;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods for creating, retrieving, updating, and deleting status entities.
 */
@Component
public interface StatusService {
    Status getStatusEntityById(Integer id);

    List<StatusResponseDTO> getAllStatuses();

    StatusResponseDTO getStatusById(Integer id);

    StatusResponseDTO createStatus(StatusRequestDTO statusRequestDTO);

    StatusResponseDTO updateStatus(Integer id, StatusRequestDTO statusRequestDTO);

    void deleteStatus(Integer id);

    Optional<Status> getStatusByName(String name);
}
