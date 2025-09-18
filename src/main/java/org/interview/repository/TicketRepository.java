package org.interview.repository;

import org.interview.entity.Ticket;
import org.interview.entity.Vehicle;
import org.interview.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehicleAndStatus(Vehicle vehicle, TicketStatus status);
    List<Ticket> findByVehicle(Vehicle vehicle);
}
