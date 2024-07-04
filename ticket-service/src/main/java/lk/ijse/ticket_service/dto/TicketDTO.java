package lk.ijse.ticket_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.ticket_service.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TicketDTO {

    @Null
    private String ticketId;

    @Null
    private TicketStatus status;

    @Null
    private Date issuedAt;

    @Null
    private Date collectedAt;

    @NotBlank(message = "Issued Location cannot be null")
    private String issuedLocation;

    @Null
    private String collectedLocation;

    @NotBlank(message = "Vehicle id cannot be null")
    private String vehicleId;

}
