package org.example.dto.responsedto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
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
