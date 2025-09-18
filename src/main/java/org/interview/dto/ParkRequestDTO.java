package org.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.interview.enums.VehicleType;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ParkRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String plateNo;
    private VehicleType vehicleType;
    private Long lotId;

}