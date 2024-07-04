package lk.ijse.ticket_service.service.impl;

import lk.ijse.ticket_service.dto.TicketDTO;
import lk.ijse.ticket_service.entity.TicketEntity;
import lk.ijse.ticket_service.enums.TicketStatus;
import lk.ijse.ticket_service.exception.NotFoundException;
import lk.ijse.ticket_service.repository.TicketRepository;
import lk.ijse.ticket_service.service.TicketService;
import lk.ijse.ticket_service.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;
    private final Conversion conversion;
    private final RestTemplate restTemplate;

    @Override
    public void createTicket(TicketDTO ticketDTO) {
        var isVehicleExists = restTemplate.getForObject("http://vehicle-service/api/v1/vehicles/exists?id=" + ticketDTO.getVehicleId(), Boolean.class);
        if (Boolean.FALSE.equals(isVehicleExists)){
            throw new NotFoundException("Vehicle not found");
        }
        TicketEntity ticketEntity = conversion.toTicketEntity(ticketDTO);
        ticketEntity.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()));
        ticketEntity.setStatus(TicketStatus.PENDING);
        ticketRepo.save(ticketEntity);
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return conversion.toTicketDTOList(ticketRepo.findAll());
    }

    @Override
    public TicketDTO getSelectedTicket(String id) {
        if (!ticketRepo.existsById(id)) throw new NotFoundException("Ticket not found");
        return conversion.toTicketDTO(ticketRepo.getReferenceById(id));
    }

    @Override
    public void updateTicketStatus(String id, String collectedLocation) {
        Optional<TicketEntity> ticketEntity = ticketRepo.findById(id);
        if (ticketEntity.isEmpty()) throw new NotFoundException("Ticket not found");
        ticketEntity.get().setStatus(TicketStatus.PAID);
        ticketEntity.get().setCollectedLocation(collectedLocation);
        ticketEntity.get().setCollectedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public boolean isTicketExists(String id) {
        return ticketRepo.existsById(id);
    }
}
