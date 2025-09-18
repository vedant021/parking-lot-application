package org.interview.controller;

import org.interview.dto.ExitRequestDTO;
import org.interview.dto.ParkRequestDTO;
import org.interview.dto.TicketDTO;
import org.interview.entity.Ticket;
import org.interview.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/enter")
    public TicketDTO parkVehicle(@RequestBody ParkRequestDTO req, Principal principal) {
        Ticket ticket = parkingService.parkVehicle(req.getPlateNo(), req.getVehicleType(), req.getLotId(), principal.getName());
        return toDTO(ticket);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/exit")
    public Map<String, Object> exitVehicle(@RequestBody ExitRequestDTO req, Principal principal) {
        return parkingService.exitVehicle(req.getTicketId(), principal.getName());
    }

    private TicketDTO toDTO(Ticket ticket) {
        if (ticket == null) return null;
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setVehicleId(ticket.getVehicle() != null ? ticket.getVehicle().getId() : null);
        dto.setSlotId(ticket.getSlot() != null ? ticket.getSlot().getId() : null);
        dto.setEntryTime(ticket.getEntryTime());
        dto.setExitTime(ticket.getExitTime());
        dto.setStatus(ticket.getStatus());
        return dto;
    }
}