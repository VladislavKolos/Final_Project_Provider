package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tariff",
        indexes = {
                @Index(name = "idx_tariff_name", columnList = "tariff_name")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 50)
    @Column(name = "tariff_name", unique = true)
    private String name;

    @Size(max = 50)
    @Column(name = "description")
    private String description;

    @DecimalMin(value = "4.99")
    @Column(name = "monthly_cost")
    private BigDecimal monthlyCost;

    @Min(value = 50)
    @Max(value = 100000)
    @Column(name = "data_limit")
    private double dataLimit;

    @Min(value = 50)
    @Max(value = 10000)
    @Column(name = "voice_limit")
    private double voiceLimit;

    @OneToMany(mappedBy = "tariff")
    private List<Plan> plans;

}
