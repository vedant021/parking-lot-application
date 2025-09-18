package org.interview.controller;


import org.interview.dto.PaymentDTO;
import org.interview.entity.Payment;
import org.interview.entity.Ticket;
import org.interview.service.PaymentService;
import org.interview.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/pay")
    public PaymentDTO pay(@RequestBody PaymentDTO paymentDto) {
        Ticket ticket = ticketService.getTicket(paymentDto.getTicketId());
        Payment payment = new Payment();
        payment.setTicket(ticket);
        payment.setAmount(paymentDto.getAmount());
        payment.setStatus(paymentDto.getStatus());
        payment.setTimestamp(paymentDto.getTimestamp());
        Payment saved = paymentService.pay(payment);
        return toDTO(saved);
    }

    private PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setTicketId(payment.getTicket() != null ? payment.getTicket().getId() : null);
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setTimestamp(payment.getTimestamp());
        return dto;
    }
}