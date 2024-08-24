package org.example.mapper;

import org.example.dto.request_dto.RoleRequestDTO;
import org.example.dto.response_dto.RoleResponseDTO;
import org.example.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO toRoleResponseDTO(Role role);

    @Mapping(target = "id", ignore = true)
    Role toRoleForCreate(RoleRequestDTO roleRequestDTO);
}
