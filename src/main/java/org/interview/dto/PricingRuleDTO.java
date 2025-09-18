package org.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.interview.enums.VehicleType;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PricingRuleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private VehicleType vehicleType;
    private int initialHours;
    private BigDecimal initialFee;
    private BigDecimal hourlyFee;

}