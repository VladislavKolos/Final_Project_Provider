package org.example.dto.requestdto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.customannotation.NoExistTariffName;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTariffRequestDTO {

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
