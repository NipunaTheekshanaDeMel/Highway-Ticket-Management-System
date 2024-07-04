package lk.ijse.vehicle_service.controllers;

import jakarta.validation.Valid;
import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.exception.DuplicateException;
import lk.ijse.vehicle_service.exception.NotFoundException;
import lk.ijse.vehicle_service.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @GetMapping(value = "/health")
    public String health(){
        return "Vehicle Ok";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerVehicle(@Valid @RequestBody VehicleDTO vehicleDTO, BindingResult bindingResult){
        logger.info("Received request for register a vehicle");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            vehicleService.registerVehicle(vehicleDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle registered successfully");
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find user with id: " + vehicleDTO.getUserId());
        } catch (DuplicateException e){
            logger.warn("Duplicate error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration Number already exists");
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllVehicles(){
        logger.info("Received request for get all vehicle details");
        try {
            return ResponseEntity.ok(vehicleService.getAllVehicles());
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSelectedVehicle(@PathVariable("id") String id){
        logger.info("Received request for get a vehicle detail");
        try {
            return ResponseEntity.ok(vehicleService.getSelectedVehicle(id));
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find Vehicle with id: " + id);
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateVehicle(@PathVariable("id") String id,
                                        @RequestBody VehicleDTO vehicleDTO,
                                        BindingResult bindingResult){
        logger.info("Received request for update a vehicle");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            vehicleService.updateVehicle(id, vehicleDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Vehicle updated successfully");
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find Vehicle with id: " + id);
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/exists")
    public ResponseEntity<?> isVehicleExists(@RequestParam String id){
        logger.info("Received request for check vehicle exists");
        try {
            return ResponseEntity.ok(vehicleService.isVehicleExists(id));
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
