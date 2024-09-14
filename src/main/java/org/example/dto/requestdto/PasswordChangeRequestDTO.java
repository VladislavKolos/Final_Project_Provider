package org.example.dto.requestdto;

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
public class PasswordChangeRequestDTO {

    @NotBlank
    @Size(min = 8, max = 256)
    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 256)
    private String newPassword;

    @NotBlank
    @Size(min = 8, max = 256)
    private String confirmNewPassword;
}
