package org.example.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDTO {

    @Size(min = 3, max = 32)
    private String username;

    @Email
    private String email;

    @Size(min = 10, max = 18)
    private String phone;
}
