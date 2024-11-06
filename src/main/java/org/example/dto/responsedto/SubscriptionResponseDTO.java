package org.example.dto.responsedto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDTO {
    private int id;
    private String status;
    private UserResponseDTO user;
    private PlanResponseDTO plan;
}
