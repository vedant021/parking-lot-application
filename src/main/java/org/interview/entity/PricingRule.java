package org.interview.entity;

import lombok.Data;
import org.interview.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pricing_rule")
public class PricingRule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "initial_hours", nullable = false)
    private int initialHours;

    @Column(name = "initial_fee", nullable = false)
    private BigDecimal initialFee;

    @Column(name = "hourly_fee", nullable = false)
    private BigDecimal hourlyFee;

}