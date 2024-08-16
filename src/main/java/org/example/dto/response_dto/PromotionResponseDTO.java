package org.example.dto.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponseDTO {
    private int id;
    private String title;
    private String description;
    private BigDecimal discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
}
