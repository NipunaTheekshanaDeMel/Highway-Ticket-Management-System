package lk.ijse.vehicle_service.repository;

import lk.ijse.vehicle_service.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {
    boolean existsByRegistrationNo(String registrationNo);
}
