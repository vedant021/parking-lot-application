package org.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.interview.enums.TicketStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TicketDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long vehicleId;
    private Long slotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private TicketStatus status;

}