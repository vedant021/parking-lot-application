package org.interview.service;

import org.interview.entity.Payment;
import org.interview.entity.Ticket;
import org.interview.enums.PaymentStatus;
import org.interview.enums.TicketStatus;
import org.interview.repository.PaymentRepository;
import org.interview.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public Payment pay(Payment payment) {
        payment.setTimestamp(LocalDateTime.now());
        payment.setStatus(PaymentStatus.SUCCESS);
        // Mark ticket as PAID
        Ticket ticket = payment.getTicket();
        if (ticket != null) {
            ticket.setStatus(TicketStatus.PAID);
            ticketRepository.save(ticket);
        }
        return paymentRepository.save(payment);
    }
}