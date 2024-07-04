package lk.ijse.user_service.user_service.service;

import lk.ijse.user_service.user_service.dto.CredentialDTO;
import lk.ijse.user_service.user_service.dto.UserDTO;

public interface UserService {

    void registerUser(UserDTO userDTO);

    void updateUser(String id,UserDTO userDTO);

    boolean isUserExists(String id);

    boolean verifyUser(CredentialDTO credentialDTO);

}
