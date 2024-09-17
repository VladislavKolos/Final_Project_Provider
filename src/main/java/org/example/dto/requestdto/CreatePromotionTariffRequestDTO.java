package org.example.dto.requestdto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionTariffRequestDTO {
    private int tariffId;
    private int promotionId;
}
