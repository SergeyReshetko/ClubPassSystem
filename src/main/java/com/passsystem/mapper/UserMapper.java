package com.passsystem.mapper;

import com.passsystem.entity.UserEntity;
import com.passsystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getMiddleName(),
                userEntity.getQrCodeEntity()
        );
    }
    
    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.id(),
                user.firstName(),
                user.lastName(),
                user.middleName(),
                user.qrCodeEntity()
        );
    }
}
