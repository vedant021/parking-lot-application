package org.interview.entity;

import lombok.Data;
import org.interview.enums.SlotStatus;
import org.interview.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "parking_slot")
public class ParkingSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private ParkingLot lot;

    private int floor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status;

}
