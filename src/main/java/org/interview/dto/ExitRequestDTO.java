package org.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ExitRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long ticketId;
}