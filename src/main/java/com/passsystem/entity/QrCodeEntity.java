package com.passsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.passsystem.dto.QrCodeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "qr-code")
public class QrCodeEntity {
    
    @Id
    @Column(name = "qr_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "qr_code", columnDefinition = "UUID", unique = true, nullable = false)
    private UUID qrCode;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @JsonBackReference("user-qrcode")
    private UserEntity userEntity;
    
    public void generateQrCode() {
        this.qrCode = UUID.randomUUID();
    }
    
    public QrCodeDto toDto() {
        if (userEntity == null) {
            return null;
        }
        
        QrCodeDto qrCodeDto = new QrCodeDto();
        qrCodeDto.setId(id);
        qrCodeDto.setQrCode(qrCode);
        
        return qrCodeDto;
    }
}

