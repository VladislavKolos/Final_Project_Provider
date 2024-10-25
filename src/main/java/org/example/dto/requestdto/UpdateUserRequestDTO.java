package org.example.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDTO {

    @Size(min = 3, max = 32)
    private String username;

    @Size(min = 8, max = 256)
    private String password;

    @Email
    private String email;

    @Size(min = 10, max = 18)
    private String phone;

    private int roleId;

    private int statusId;
}
