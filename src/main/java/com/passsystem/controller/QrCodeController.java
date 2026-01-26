package com.passsystem.controller;

import com.passsystem.dto.QrCodeDto;
import com.passsystem.dto.UserDto;
import com.passsystem.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/qr")
@RequiredArgsConstructor
public class QrCodeController {
    
    private final QrCodeService qrCodeService;
    
    @GetMapping("/{qrCode}")
    public ResponseEntity<QrCodeDto> getQrCodeByUUID(@PathVariable("qrCode") UUID qrCode) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.getQrCodeByUUID(qrCode));
    }
    
    @PutMapping("/{qrCode}")
    public ResponseEntity<QrCodeDto> updateQrCode(@PathVariable("qrCode") UUID qrCode) {
        var updated = qrCodeService.findByQrCode(qrCode);
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.updateQrCode(updated));
    }
    
    @DeleteMapping("/{qrCode}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable("qrCode") UUID qrCode) {
        var deleted = qrCodeService.findByQrCode(qrCode);
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.deleteUser(deleted));
    }
}
