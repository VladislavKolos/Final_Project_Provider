package org.example.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.annotation.customannotation.NoExistRoleName;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {

    @NotBlank
    @Size(min = 4, max = 11)
    @NoExistRoleName
    private String name;
}
