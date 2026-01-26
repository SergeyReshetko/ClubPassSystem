package com.passsystem.mapper;

import com.passsystem.dto.UserDto;
import com.passsystem.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public UserDto toDomain(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setMiddleName(userEntity.getMiddleName());
        if (userEntity.getQrCodeEntity() != null) {
            userDto.setQrCodeDto(new QrCodeMapper().toDomain(userEntity.getQrCodeEntity()));
        }
        return userDto;
    }
    
    public UserEntity toEntity(UserDto user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setMiddleName(user.getMiddleName());
        return userEntity;
    }
}
