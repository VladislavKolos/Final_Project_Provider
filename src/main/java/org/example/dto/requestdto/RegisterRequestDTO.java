package org.example.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @Size(min = 3, max = 32)
    @NotBlank
    private String username;

    @Size(min = 8, max = 256)
    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @Size(min = 10, max = 18)
    @NotBlank
    private String phone;
}
