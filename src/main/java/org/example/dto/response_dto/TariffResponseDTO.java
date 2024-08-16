package org.example.dto.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TariffResponseDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal monthlyCost;
    private double dataLimit;
    private double voiceLimit;
}
