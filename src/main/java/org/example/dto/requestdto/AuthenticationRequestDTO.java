package org.example.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.annotation.customannotation.ValidPassword;
import org.example.annotation.customannotation.ValidUsername;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidPassword
public class AuthenticationRequestDTO {

    @Size(min = 3, max = 32)
    @NotBlank
    @ValidUsername
    private String username;

    @Size(min = 8, max = 256)
    @NotBlank
    private String password;
}
