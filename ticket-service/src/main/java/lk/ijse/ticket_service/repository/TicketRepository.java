package lk.ijse.ticket_service.repository;

import lk.ijse.ticket_service.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, String> {
}
