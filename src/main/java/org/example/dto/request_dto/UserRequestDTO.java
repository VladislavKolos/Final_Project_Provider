package org.example.dto.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    @Size(min = 3, max = 32)
    private String username;

    @NotBlank
    @Size(min = 8, max = 256)
    private String password;

    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 18)
    private String phone;

    @Size(min = 6, max = 8)
    private String status;

    private int roleId;
}
