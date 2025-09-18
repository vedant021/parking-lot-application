package org.interview.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.interview.enums.SlotStatus;
import org.interview.enums.VehicleType;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ParkingSlotDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long lotId;
    private int floor;
    private VehicleType type;
    private SlotStatus status;

}