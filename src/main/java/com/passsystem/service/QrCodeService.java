package com.passsystem.service;

import com.passsystem.dto.QrCodeDto;
import com.passsystem.dto.UserDto;
import com.passsystem.entity.QrCodeEntity;
import com.passsystem.entity.UserEntity;
import com.passsystem.mapper.QrCodeMapper;
import com.passsystem.mapper.UserMapper;
import com.passsystem.repository.QrCodeRepository;
import com.passsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QrCodeService {
    
    private static final Logger logger = LoggerFactory.getLogger(QrCodeService.class);
    
    private final QrCodeRepository qrCodeRepository;
    private final UserRepository userRepository;
    private final QrCodeMapper qrCodeMapper;
    private final UserMapper userMapper;
    
    public QrCodeDto getQrCodeByUUID(UUID qrCode) {
        logger.info("getUserById: qrCode={}", qrCode);
        
        QrCodeEntity qrCodeEntity = qrCodeRepository.findQrCodeInfoById(qrCode)
                                            .orElseThrow(() -> new EntityNotFoundException(
                                                    "No qrCode found with id " + qrCode
                                            ));
        return qrCodeMapper.toDomain(qrCodeEntity);
    }
    
    @Transactional
    public QrCodeDto updateQrCode(UUID qrCode) {
        logger.info("Called updateQrCode: qrCode= {}", qrCode);
        var qrCodeEntity = qrCodeRepository.findQrCodeInfoById(qrCode);
        if (qrCodeEntity.isEmpty()) {
            throw new EntityNotFoundException("No qrCode found with id " + qrCode);
        }
        qrCodeEntity.get().generateQrCode();
        return qrCodeMapper.toDomain(qrCodeEntity.get());
    }
    
    public UserDto deleteUser(UUID qrCode) {
        logger.info("deleteUserById: qrCode={}", qrCode);
        var qrCodeEntity = qrCodeRepository.findQrCodeInfoById(qrCode);
        var userEntity = findUserById(qrCodeEntity);
        UserDto userDto = userMapper.toDomain(userEntity);
        userRepository.delete(userEntity);
        return userDto;
    }
    
    private UserEntity findUserById(Optional<QrCodeEntity> qrCodeEntity) {
        logger.info("findUserById: qrCodeEntity={}", qrCodeEntity);
        Long userId = qrCodeEntity
                              .orElseThrow(() -> new EntityNotFoundException("Not found qrCodeEntity by userId = " + qrCodeEntity))
                              .getUserEntity()
                              .getId();
        return userRepository.findById(userId)
                       .orElseThrow(() -> new EntityNotFoundException("Not found user by userId = " + userId));
    }
}
