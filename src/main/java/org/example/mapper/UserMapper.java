package org.example.mapper;

import org.example.dto.requestdto.UpdateUserRequestDTO;
import org.example.dto.responsedto.UserResponseDTO;
import org.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {RoleMapper.class, StatusMapper.class})
public interface UserMapper {

    @Mapping(source = "role", target = "role")
    @Mapping(source = "status", target = "status")
    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "statusId", target = "status.id")
    User toUserForCreate(UpdateUserRequestDTO userRequestDTO);
}
