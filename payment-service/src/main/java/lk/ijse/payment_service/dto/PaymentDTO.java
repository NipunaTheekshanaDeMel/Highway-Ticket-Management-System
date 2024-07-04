package lk.ijse.payment_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.payment_service.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentDTO {

    @Null
    private String paymentId;

    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount cannot be negative value")
    private Double amount;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @NotBlank(message = "Ticket Id cannot be blank")
    private String ticketId;

}
