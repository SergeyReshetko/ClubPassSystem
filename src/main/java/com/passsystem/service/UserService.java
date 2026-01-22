package com.passsystem.service;

import com.passsystem.entity.QrCodeEntity;
import com.passsystem.entity.UserEntity;
import com.passsystem.mapper.UserMapper;
import com.passsystem.model.User;
import com.passsystem.model.UserSearchFilter;
import com.passsystem.repository.QrCodeRepository;
import com.passsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final QrCodeRepository qrCodeRepository;
    private final UserMapper userMapper;
    
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "No user found with id " + id
                                        ));
        return userMapper.toDomain(userEntity);
    }
    
    public User addUser(User user) {
        var entityUserToSave = userMapper.toEntity(user);
        entityUserToSave = userRepository.save(entityUserToSave);
        createQrCode(entityUserToSave);
        
        return userMapper.toDomain(entityUserToSave);
    }
    
    
    public List<User> searchAllByFilter(UserSearchFilter filter) {
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 10;
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;
        
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        
        List<UserEntity> allEntities = userRepository.searchAllByFilter(pageable);
        
        return allEntities.stream()
                       .map(userMapper::toDomain)
                       .toList();
    }
    
    public User updateQrCode(Long qrCode) {
        var qrCodeEntity = qrCodeRepository.findQrCodeInfoById(qrCode);
        UserEntity userEntity;
        if (qrCodeEntity == null) {
            throw new EntityNotFoundException("No qrCode found with id " + qrCode);
        } else {
            userEntity = findUserById(qrCodeEntity);
            qrCodeRepository.deleteByUserId(userEntity.getId());
        }
        createQrCode(userEntity);
        return userMapper.toDomain(userEntity);
    }
    
    public void deleteUser(Long qrCode) {
        var qrCodeEntity = qrCodeRepository.findQrCodeInfoById(qrCode);
        var userEntity = findUserById(qrCodeEntity);
        userRepository.delete(userEntity);
    }
    
    private void createQrCode(UserEntity userEntity) {
        QrCodeEntity qrCodeEntity = new QrCodeEntity();
        qrCodeEntity.setUserEntity(userEntity);
        qrCodeRepository.save(qrCodeEntity);
    }
    
    private UserEntity findUserById(QrCodeEntity qrCodeEntity) {
        Long userId = qrCodeEntity.getUserEntity().getId();
        return userRepository.findById(userId)
                       .orElseThrow(() -> new EntityNotFoundException("Not found user by userId = " + userId));
    }
}
