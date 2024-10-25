package org.example.dto.responsedto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private String token;
}
