package org.example.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int id;
    private String username;
    private String email;
    private String phone;
    private RoleResponseDTO role;
    private StatusResponseDTO status;

}
