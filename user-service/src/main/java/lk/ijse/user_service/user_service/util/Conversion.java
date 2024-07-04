package lk.ijse.user_service.user_service.util;

import lk.ijse.user_service.user_service.dto.UserDTO;
import lk.ijse.user_service.user_service.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Conversion {

    final private ModelMapper modelMapper;

    public UserEntity convertUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }

}
