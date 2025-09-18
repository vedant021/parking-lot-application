package org.interview.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "parking_lot")
public class ParkingLot implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private int floors;

    // Optional: OneToMany relationship to ParkingSlot if you want
    // @OneToMany(mappedBy = "lot")
    // private List<ParkingSlot> slots;
}