package lk.ijse.vehicle_service.service.impl;

import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.entity.VehicleEntity;
import lk.ijse.vehicle_service.exception.DuplicateException;
import lk.ijse.vehicle_service.exception.NotFoundException;
import lk.ijse.vehicle_service.repository.VehicleRepository;
import lk.ijse.vehicle_service.service.VehicleService;
import lk.ijse.vehicle_service.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepo;
    private final Conversion conversion;
    private final RestTemplate restTemplate;

    @Override
    public void registerVehicle(VehicleDTO vehicleDTO) {
        var isUserExists = restTemplate.getForObject("http://user-service/api/v1/users?id=" + vehicleDTO.getUserId(), Boolean.class);
        if (Boolean.FALSE.equals(isUserExists)){
            throw new NotFoundException("User not found");
        }
        if (vehicleRepo.existsByRegistrationNo(vehicleDTO.getRegistrationNo())){
            throw new DuplicateException("Registration Number already exists");
        }
        vehicleRepo.save(conversion.toVehicleEntity(vehicleDTO));
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return conversion.toVehicleDTOList(vehicleRepo.findAll());
    }

    @Override
    public VehicleDTO getSelectedVehicle(String id) {
        if (!vehicleRepo.existsById(id)) throw new NotFoundException("Vehicle not found");
        return conversion.toVehicleDTO(vehicleRepo.getReferenceById(id));
    }

    @Override
    public void updateVehicle(String id, VehicleDTO vehicleDTO) {
        Optional<VehicleEntity> vehicleEntity = vehicleRepo.findById(id);
        if (vehicleEntity.isEmpty()) throw new NotFoundException("Vehicle not found");
        vehicleEntity.get().setRegistrationNo(vehicleDTO.getRegistrationNo());
        vehicleEntity.get().setModel(vehicleDTO.getModel());
        vehicleEntity.get().setYear(vehicleDTO.getYear());
    }

    @Override
    public boolean isVehicleExists(String id) {
        return vehicleRepo.existsById(id);
    }
}
