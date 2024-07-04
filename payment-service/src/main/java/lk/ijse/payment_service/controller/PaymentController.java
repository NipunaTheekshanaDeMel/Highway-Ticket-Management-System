package lk.ijse.payment_service.controller;

import jakarta.validation.Valid;
import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.exception.NotFoundException;
import lk.ijse.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping(value = "/health")
    public String health(){
        return "Payment ok";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentDTO paymentDTO, BindingResult bindingResult){
        logger.info("Received request for create new payment");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            paymentService.createPayment(paymentDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully");
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find Ticket with id: " + paymentDTO.getTicketId());
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
