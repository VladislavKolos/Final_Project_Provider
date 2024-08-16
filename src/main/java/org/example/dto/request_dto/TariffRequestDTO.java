package org.example.dto.request_dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffRequestDTO {

    @NotBlank
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
