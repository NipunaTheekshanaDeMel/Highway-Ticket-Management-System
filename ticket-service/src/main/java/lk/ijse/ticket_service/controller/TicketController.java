package lk.ijse.ticket_service.controller;

import jakarta.validation.Valid;
import lk.ijse.ticket_service.dto.TicketDTO;
import lk.ijse.ticket_service.exception.NotFoundException;
import lk.ijse.ticket_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @GetMapping(value = "/health")
    public String health(){
        return "Ticket ok";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketDTO ticketDTO, BindingResult bindingResult){
        logger.info("Received request for create new ticket");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            ticketService.createTicket(ticketDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Ticket created successfully");
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find Vehicle with id: " + ticketDTO.getVehicleId());
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllVehicles(){
        logger.info("Received request for get all tickets details");
        try {
            return ResponseEntity.ok(ticketService.getAllTickets());
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSelectedTicket(@PathVariable("id") String id){
        logger.info("Received request for get a ticket detail");
        try {
            return ResponseEntity.ok(ticketService.getSelectedTicket(id));
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find Ticket with id: " + id);
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTicketStatus(@PathVariable("id") String id, @RequestParam String collectedLocation){
        logger.info("Received request for update a ticket status");
        try {
            ticketService.updateTicketStatus(id, collectedLocation);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.OK).body("Ticket updated successfully");
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find Ticket with id: " + id);
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/exists")
    public ResponseEntity<?> isTicketExists(@RequestParam String id){
        logger.info("Received request for check ticket exists");
        try {
            return ResponseEntity.ok(ticketService.isTicketExists(id));
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
