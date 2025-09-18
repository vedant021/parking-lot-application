package org.interview.entity;

import lombok.Data;
import org.interview.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_no", nullable = false, unique = true)
    private String plateNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}