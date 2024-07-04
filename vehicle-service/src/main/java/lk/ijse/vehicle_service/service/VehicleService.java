package lk.ijse.vehicle_service.service;

import lk.ijse.vehicle_service.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {

    void registerVehicle(VehicleDTO vehicleDTO);

    List<VehicleDTO> getAllVehicles();

    VehicleDTO getSelectedVehicle(String id);

    void updateVehicle(String id, VehicleDTO vehicleDTO);

    boolean isVehicleExists(String id);

}
