package org.example.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDTO {
    private int id;
    private String status;
    private UserResponseDTO user;
    private PlanResponseDTO plan;
}
