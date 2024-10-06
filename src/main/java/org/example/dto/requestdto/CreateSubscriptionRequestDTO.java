package org.example.dto.requestdto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.annotation.customannotation.NoExistUserIdInSub;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriptionRequestDTO {

    @Size(min = 6, max = 20)
    private String status;

    @NoExistUserIdInSub
    private int userId;

    private int planId;
}
