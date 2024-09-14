package org.example.dto.requestdto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.customannotation.ExistTariffId;
import org.example.annotation.customannotation.NoExistPlanName;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanRequestDTO {

    @NoExistPlanName
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @ExistTariffId
    private int tariffId;
}

