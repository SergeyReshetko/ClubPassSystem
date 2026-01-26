package com.passsystem.service;

import com.passsystem.dto.QrCodeDto;
import com.passsystem.dto.UserDto;
import com.passsystem.entity.QrCodeEntity;
import com.passsystem.entity.UserEntity;
import com.passsystem.filter.UserSearchFilter;
import com.passsystem.mapper.QrCodeMapper;
import com.passsystem.mapper.UserMapper;
import com.passsystem.repository.QrCodeRepository;
import com.passsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    private final QrCodeRepository qrCodeRepository;
    private final UserMapper userMapper;
    private final QrCodeMapper qrCodeMapper;
    @Value("${app.pageSize:10}")
    private int pageSize;
    @Value("${app.pageNumber:10}")
    private int pageNumber;
    
    public UserDto getUserById(Long id) {
        logger.info("getUserById: id={}", id);
        UserEntity userEntity = userRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "No user found with id " + id
                                        ));
        return userMapper.toDomain(userEntity);
    }
    
    public List<UserDto> searchAllByFilter(UserSearchFilter filter) {
        logger.info("Called searchAllByFilter");
        this.pageSize = filter.pageSize();
        this.pageNumber = filter.pageNumber();
        
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        Page<UserEntity> allEntities = userRepository.findAll(pageable);
        
        return allEntities.stream()
                       .map(userMapper::toDomain)
                       .toList();
    }
    
    @Transactional
    public UserDto createUser(UserDto user) {
        logger.info("addUser: user={}", user);
        var entityUserToSave = userMapper.toEntity(user);
        entityUserToSave = userRepository.save(entityUserToSave);
        UserDto userDto = userMapper.toDomain(entityUserToSave);
        userDto.setQrCodeDto(createQrCode(entityUserToSave));
        
        return userDto;
    }
    
    private QrCodeDto createQrCode(UserEntity userEntity) {
        QrCodeEntity qrCodeEntity = new QrCodeEntity();
        qrCodeEntity.setUserEntity(userEntity);
        qrCodeEntity.setQrCode(UUID.randomUUID());
        qrCodeRepository.save(qrCodeEntity);
        
        return qrCodeMapper.toDomain(qrCodeEntity);
    }
}
