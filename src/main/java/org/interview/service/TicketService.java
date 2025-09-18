package org.interview.service;

import org.interview.entity.Ticket;
import org.interview.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository repo;

    public Ticket getTicket(Long id) {
        return repo.findById(id).orElse(null);
    }
}