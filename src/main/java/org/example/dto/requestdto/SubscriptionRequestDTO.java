package org.example.dto.requestdto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDTO {

    @Size(min = 6, max = 20)
    private String status;

    private int userId;

    private int planId;
}
