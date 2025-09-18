package org.interview.controller;


import org.interview.dto.ParkingSlotDTO;
import org.interview.entity.ParkingSlot;
import org.interview.enums.VehicleType;
import org.interview.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/slots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService slotService;

    @GetMapping("/available")
    public List<ParkingSlotDTO> getAvailableSlots(@RequestParam VehicleType type) {
        return slotService.getAvailableSlots(type).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ParkingSlotDTO addSlot(@RequestBody ParkingSlotDTO slotDto) {
        ParkingSlot slot = slotService.addSlot(fromDTO(slotDto));
        return toDTO(slot);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
    }

    private ParkingSlotDTO toDTO(ParkingSlot slot) {
        if (slot == null) return null;
        ParkingSlotDTO dto = new ParkingSlotDTO();
        dto.setId(slot.getId());
        dto.setLotId(slot.getLot() != null ? slot.getLot().getId() : null);
        dto.setFloor(slot.getFloor());
        dto.setType(slot.getType());
        dto.setStatus(slot.getStatus());
        return dto;
    }

    private ParkingSlot fromDTO(ParkingSlotDTO dto) {
        ParkingSlot slot = new ParkingSlot();
        slot.setId(dto.getId());
        // You may need to fetch ParkingLot by id if required
        slot.setFloor(dto.getFloor());
        slot.setType(dto.getType());
        slot.setStatus(dto.getStatus());
        return slot;
    }
}