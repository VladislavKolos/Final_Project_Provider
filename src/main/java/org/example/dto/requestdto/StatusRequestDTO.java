package org.example.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.annotation.customannotation.NoExistStatusName;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequestDTO {

    @NotBlank
    @Size(min = 6, max = 8)
    @NoExistStatusName
    private String name;
}
