package org.example.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionTariffResponseDTO {
    private int id;
    private TariffResponseDTO tariff;
    private PromotionResponseDTO promotion;

}
