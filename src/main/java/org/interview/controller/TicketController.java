package org.interview.controller;

import org.interview.dto.TicketDTO;
import org.interview.entity.Ticket;
import org.interview.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public TicketDTO getTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicket(id);
        return toDTO(ticket);
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