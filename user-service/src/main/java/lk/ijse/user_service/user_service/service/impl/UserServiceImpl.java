package lk.ijse.user_service.user_service.service.impl;

import lk.ijse.user_service.user_service.dto.CredentialDTO;
import lk.ijse.user_service.user_service.dto.UserDTO;
import lk.ijse.user_service.user_service.entity.UserEntity;
import lk.ijse.user_service.user_service.exception.DuplicateException;
import lk.ijse.user_service.user_service.exception.NotFoundException;
import lk.ijse.user_service.user_service.repository.UserRepository;
import lk.ijse.user_service.user_service.service.UserService;
import lk.ijse.user_service.user_service.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final Conversion conversion;

    @Override
    public void registerUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())){
            throw new DuplicateException("Email us already exists");
        }
        userRepo.save(conversion.convertUserEntity(userDTO));
    }

    @Override
    public void updateUser(String id, UserDTO userDTO) {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) throw new NotFoundException("User Not Found");
        userEntity.get().setName(userDTO.getName());
        userEntity.get().setEmail(userDTO.getEmail());
        userEntity.get().setPassword(userDTO.getPassword());
        userEntity.get().setRole(userDTO.getRole());
        System.out.println(userEntity.get());
    }

    @Override
    public boolean isUserExists(String id) {
        return userRepo.existsById(id);
    }

    @Override
    public boolean verifyUser(CredentialDTO credentialDTO) {
        Optional<UserEntity> userEntity = userRepo.findByEmail(credentialDTO.getEmail());
        return userEntity.filter(entity -> credentialDTO.getPassword().equals(entity.getPassword())).isPresent();
    }

}
