package lk.ijse.ticket_service.entity;

import jakarta.persistence.*;
import lk.ijse.ticket_service.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date collectedAt;

    private String issuedLocation;

    private String collectedLocation;

    private String vehicleId;

}
