package org.example.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.annotation.customannotation.NoExistPlanName;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanRequestDTO {

    @NotBlank
    @NoExistPlanName
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private int tariffId;
}
