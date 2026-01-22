package com.passsystem.repository;

import com.passsystem.entity.QrCodeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {
    
    @Query("SELECT u FROM QrCodeEntity u WHERE u.qrCode = :qrCode")
    QrCodeEntity findQrCodeInfoById(@Param("qrCode") Long qrCode);
    
    @Query("DELETE FROM QrCodeEntity q WHERE q.userEntity.id = :userId")
    @Modifying
    @Transactional
    void deleteByUserId(@Param("userId") Long userId);
}
