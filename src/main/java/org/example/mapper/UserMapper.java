package org.example.mapper;

import org.example.dto.request_dto.UserRequestDTO;
import org.example.dto.response_dto.UserResponseDTO;
import org.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "role.id", target = "role.id")
    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "role.id", target = "role.id")
    User toUserForCreate(UserRequestDTO userRequestDTO);
}
