package org.example.dto.requestdto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class PromotionRequestDTO {

    private int id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 100)
    private String description;

    @DecimalMin(value = "5")
    private BigDecimal discountPercentage;

    private LocalDate startDate;

    private LocalDate endDate;
}
