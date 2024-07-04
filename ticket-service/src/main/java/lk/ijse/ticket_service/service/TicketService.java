package lk.ijse.ticket_service.service;

import lk.ijse.ticket_service.dto.TicketDTO;

import java.util.List;

public interface TicketService {

    void createTicket(TicketDTO ticketDTO);

    List<TicketDTO> getAllTickets();

    TicketDTO getSelectedTicket(String id);

    void updateTicketStatus(String id, String collectedLocation);

    boolean isTicketExists(String id);

}
