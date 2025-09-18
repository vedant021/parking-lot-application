package org.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.interview.enums.VehicleType;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class VehicleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String plateNo;
    private VehicleType type;
    private Long ownerId;

}