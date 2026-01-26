package com.passsystem.repository;

import com.passsystem.entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {
    
    @Query("SELECT u FROM QrCodeEntity u WHERE u.qrCode = :qrCode")
    Optional<QrCodeEntity> findQrCodeInfoById(@Param("qrCode") UUID qrCode);
}
