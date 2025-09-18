package org.interview.service;

import org.interview.entity.*;
import org.interview.enums.PaymentStatus;
import org.interview.enums.SlotStatus;
import org.interview.enums.TicketStatus;
import org.interview.enums.VehicleType;
import org.interview.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {
    @Autowired
    private ParkingSlotRepository slotRepo;
    @Autowired
    private TicketRepository ticketRepo;
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private ParkingLotRepository lotRepo;
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private PricingRuleRepository pricingRuleRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;

    @Transactional
    public Ticket parkVehicle(String plateNo, VehicleType type, Long lotId, String userEmail) {
        // Prevent duplicate entry (active ticket)
        User user = userService.getByEmail(userEmail);
        if (user == null) throw new RuntimeException("User not found.");
        Vehicle vehicle = vehicleService.findOrCreateVehicle(plateNo, type, user);

        Optional<Ticket> activeTicket = ticketRepo.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE);
        if (activeTicket.isPresent()) throw new RuntimeException("Vehicle already has an active ticket.");

        List<ParkingSlot> availableSlots = slotRepo.findAvailableSlotsForType(type, SlotStatus.AVAILABLE);
        if (availableSlots.isEmpty()) throw new RuntimeException("No slot available for this vehicle type.");

        ParkingSlot slot = availableSlots.get(0); // Nearest/first strategy
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepo.save(slot);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setSlot(slot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.ACTIVE);
        return ticketRepo.save(ticket);
    }

    @Transactional
    public Map<String, Object> exitVehicle(Long ticketId, String userEmail) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(
                () -> new RuntimeException("Ticket not found"));
        if (!ticket.getStatus().equals(TicketStatus.ACTIVE)) {
            throw new RuntimeException("Ticket not active or already paid.");
        }
        ticket.setExitTime(LocalDateTime.now());

        // Calculate fee (example: first 2h free, hourly after, per vehicle type)
        PricingRule rule = pricingRuleRepo.findByVehicleType(ticket.getVehicle().getType())
                .orElseThrow(() -> new RuntimeException("No pricing rule for type"));
        long hours = java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        if (hours < rule.getInitialHours()) hours = rule.getInitialHours();
        double fee = rule.getInitialFee().doubleValue();
        if (hours > rule.getInitialHours()) {
            fee += (hours - rule.getInitialHours()) * rule.getHourlyFee().doubleValue();
        }

        // Generate payment (simulate payment process)
        Payment payment = new Payment();
        payment.setTicket(ticket);
        payment.setAmount(new java.math.BigDecimal(fee));
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTimestamp(LocalDateTime.now());
        paymentRepo.save(payment);

        // Mark ticket as PAID, free slot
        ticket.setStatus(TicketStatus.PAID);
        ticketRepo.save(ticket);

        ParkingSlot slot = ticket.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepo.save(slot);

        Map<String, Object> receipt = new HashMap<String, Object>();
        receipt.put("ticketId", ticket.getId());
        receipt.put("amountPaid", fee);
        receipt.put("vehicle", ticket.getVehicle().getPlateNo());
        receipt.put("entryTime", ticket.getEntryTime());
        receipt.put("exitTime", ticket.getExitTime());
        receipt.put("slotId", slot.getId());
        return receipt;
    }
}