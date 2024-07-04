package lk.ijse.payment_service.util;

import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Conversion {

    private final ModelMapper modelMapper;

    public PaymentEntity toPaymentEntity(PaymentDTO paymentDTO){
        return modelMapper.map(paymentDTO, PaymentEntity.class);
    }

    public PaymentDTO toPaymentDTO(PaymentEntity paymentEntity){
        return modelMapper.map(paymentEntity, PaymentDTO.class);
    }

}
