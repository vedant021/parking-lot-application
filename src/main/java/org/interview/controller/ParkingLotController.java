package org.interview.controller;


import org.interview.dto.ParkingLotDTO;
import org.interview.entity.ParkingLot;
import org.interview.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping
    public List<ParkingLotDTO> getAll() {
        return parkingLotService.getAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParkingLotDTO get(@PathVariable Long id) {

        return toDTO(parkingLotService.get(id));
    }

    private ParkingLotDTO toDTO(ParkingLot lot) {
        if (lot == null) return null;
        ParkingLotDTO dto = new ParkingLotDTO();
        dto.setId(lot.getId());
        dto.setLocation(lot.getLocation());
        dto.setFloors(lot.getFloors());
        return dto;
    }
}