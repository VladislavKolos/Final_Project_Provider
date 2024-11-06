package org.example.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDTO {

    @Email
    private String email;

    @Size(min = 3, max = 32)
    private String username;

    @Size(min = 10, max = 18)
    private String phone;
}
