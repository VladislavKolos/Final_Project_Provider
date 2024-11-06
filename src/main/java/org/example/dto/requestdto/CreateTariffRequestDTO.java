package org.example.dto.requestdto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.annotation.customannotation.NoExistTariffName;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTariffRequestDTO {

    @NotBlank
    @NoExistTariffName
    @Size(min = 2, max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    @DecimalMin(value = "4.99")
    private BigDecimal monthlyCost;

    @Min(value = 50)
    @Max(value = 100000)
    private double dataLimit;

    @Min(value = 50)
    @Max(value = 10000)
    private double voiceLimit;
}
