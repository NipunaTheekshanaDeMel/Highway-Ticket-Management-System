package lk.ijse.user_service.user_service.controller;

import jakarta.validation.Valid;
import lk.ijse.user_service.user_service.dto.CredentialDTO;
import lk.ijse.user_service.user_service.dto.UserDTO;
import lk.ijse.user_service.user_service.exception.DuplicateException;
import lk.ijse.user_service.user_service.exception.NotFoundException;
import lk.ijse.user_service.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "/health")
    public String health(){
        return "User Ok";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
        logger.info("Received request for register a user");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            userService.registerUser(userDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (DuplicateException e){
            logger.warn("Duplicate error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("id") String id,
                                        @RequestBody UserDTO userDTO,
                                        BindingResult bindingResult){
        logger.info("Received request for update a user");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            userService.updateUser(id, userDTO);
            logger.info("Request processed successfully");
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
        } catch (NotFoundException e){
            logger.warn("Not found error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find User with id: " + id);
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> isUserExists(@RequestParam String id){
        logger.info("Received request for check user exists");
        try {
            return ResponseEntity.ok(userService.isUserExists(id));
        } catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyUser(@Valid @RequestBody CredentialDTO credentialDTO, BindingResult bindingResult){
        logger.info("Received request for verify a user");
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(userService.verifyUser(credentialDTO));
        }catch (Exception e){
            logger.error("An exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
