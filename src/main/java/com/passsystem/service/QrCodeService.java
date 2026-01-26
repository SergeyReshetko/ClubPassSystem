package com.passsystem.service;

import com.passsystem.dto.QrCodeDto;
import com.passsystem.dto.UserDto;
import com.passsystem.entity.QrCodeEntity;
import com.passsystem.entity.UserEntity;
import com.passsystem.exception.QrCodeNotFoundException;
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
                                            .orElseThrow(() -> new QrCodeNotFoundException("No qrCode found with id " + qrCode));
        return qrCodeMapper.toDomain(qrCodeEntity);
    }
    
    @Transactional
    public QrCodeEntity findByQrCode(UUID qrCode) {
        logger.info("getCodeByQrCode: qrCode={}", qrCode);
        return qrCodeRepository.findQrCodeInfoById(qrCode)
                       .orElseThrow(() -> new QrCodeNotFoundException("No qrCode found with " + qrCode));
    }
    
    @Transactional
    public QrCodeDto updateQrCode(QrCodeEntity qrCodeEntity) {
        logger.info("Called updateQrCode: qrCodeEntity= {}", qrCodeEntity.getQrCode());
        UUID qrCode = UUID.randomUUID();
        qrCodeEntity.setQrCode(qrCode);
        qrCodeRepository.save(qrCodeEntity);
        return qrCodeMapper.toDomain(qrCodeEntity);
    }
    
    @Transactional
    public UserDto deleteUser(QrCodeEntity qrCodeEntity) {
        logger.info("Called deleteQrCode: qrCodeEntity= {}", qrCodeEntity.getQrCode());
        var userEntity = findUserById(qrCodeEntity);
        UserDto userDto = userMapper.toDomain(userEntity);
        userRepository.delete(userEntity);
        return userDto;
    }
    
    private UserEntity findUserById(QrCodeEntity qrCodeEntity) {
        logger.info("findUserById: qrCodeEntity={}", qrCodeEntity.getQrCode());
        Long userId = qrCodeEntity.getUserEntity().getId();
        return userRepository.findById(userId)
                       .orElseThrow(() -> new EntityNotFoundException("Not found user by userId = " + userId));
    }
}
