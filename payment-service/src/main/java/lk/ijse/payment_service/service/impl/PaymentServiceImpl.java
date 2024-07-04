package lk.ijse.payment_service.service.impl;

import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.exception.NotFoundException;
import lk.ijse.payment_service.repository.PaymentRepository;
import lk.ijse.payment_service.service.PaymentService;
import lk.ijse.payment_service.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepo;
    private final Conversion conversion;
    private final RestTemplate restTemplate;

    @Override
    public void createPayment(PaymentDTO paymentDTO) {
        var isVehicleExists = restTemplate.getForObject("http://ticket-service/api/v1/tickets/exists?id=" + paymentDTO.getTicketId(), Boolean.class);
        if (Boolean.FALSE.equals(isVehicleExists)){
            throw new NotFoundException("Ticket not found");
        }
        paymentRepo.save(conversion.toPaymentEntity(paymentDTO));
        restTemplate.put(
                "http://ticket-service/api/v1/tickets/" + paymentDTO.getTicketId() + "?collectedLocation=" + paymentDTO.getLocation(),
                null
        );
    }
}
