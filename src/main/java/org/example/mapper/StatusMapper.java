package org.example.mapper;

import org.example.dto.requestdto.StatusRequestDTO;
import org.example.dto.responsedto.StatusResponseDTO;
import org.example.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusResponseDTO toStatusResponseDTO(Status status);

    @Mapping(target = "id", ignore = true)
    Status toStatusForCreate(StatusRequestDTO statusRequestDTO);
}
