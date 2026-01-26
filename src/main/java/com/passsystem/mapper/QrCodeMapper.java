package com.passsystem.mapper;

import com.passsystem.dto.QrCodeDto;
import com.passsystem.entity.QrCodeEntity;
import org.springframework.stereotype.Component;

@Component
public class QrCodeMapper {
    
    public QrCodeDto toDomain(QrCodeEntity qrCodeEntity) {
        QrCodeDto qrCodeDto = new QrCodeDto();
        qrCodeDto.setId(qrCodeEntity.getId());
        qrCodeDto.setQrCode(qrCodeEntity.getQrCode());
        return qrCodeDto;
        
    }
}
