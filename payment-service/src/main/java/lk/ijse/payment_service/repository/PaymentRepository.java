package lk.ijse.payment_service.repository;

import lk.ijse.payment_service.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
}
