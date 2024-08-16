package org.example.dto.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private int tariffId;
}
