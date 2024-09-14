package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.requestdto.StatusRequestDTO;
import org.example.dto.responsedto.StatusResponseDTO;
import org.example.mapper.StatusMapper;
import org.example.model.Status;
import org.example.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    @Transactional(readOnly = true)
    public Status getStatusEntityById(Integer id) {
        return statusRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<StatusResponseDTO> getAllStatuses() {
        return statusRepository.findAll()
                .stream()
                .map(statusMapper::toStatusResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public StatusResponseDTO getStatusById(Integer id) {
        return statusRepository.findById(id)
                .map(statusMapper::toStatusResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public StatusResponseDTO createStatus(StatusRequestDTO statusRequestDTO) {
        return Optional.of(statusRequestDTO)
                .map(statusMapper::toStatusForCreate)
                .map(statusRepository::save)
                .map(statusMapper::toStatusResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public StatusResponseDTO updateStatus(Integer id, StatusRequestDTO statusRequestDTO) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        status.setName(statusRequestDTO.getName());

        return Optional.of(status)
                .map(statusRepository::save)
                .map(statusMapper::toStatusResponseDTO)
                .orElseThrow();
    }

    @Transactional
    public void deleteStatus(Integer id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        statusRepository.delete(status);
    }

    @Transactional(readOnly = true)
    public Optional<Status> getStatusByName(String name) {
        return statusRepository.findByName(name);
    }
}
