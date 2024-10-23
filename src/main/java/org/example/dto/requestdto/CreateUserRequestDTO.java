package org.example.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {

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

    private int roleId;

    private int statusId;

}
