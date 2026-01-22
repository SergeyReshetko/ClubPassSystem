package com.passsystem.controller;

import com.passsystem.model.User;
import com.passsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/code")
@AllArgsConstructor
public class QrCodeController {
    
    private static final Logger logger = LoggerFactory.getLogger(QrCodeController.class);
    
    private final UserService userService;
    
    @PutMapping("/{qrCode:\\d+}")
    public ResponseEntity<User> updateQrCode(@PathVariable("qrCode") Long qrCode) {
        logger.info("Called updateQrCode: qrCode= {}", qrCode);
        var updated = userService.updateQrCode(qrCode);
        
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id:\\d+}/delete")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        logger.info("deleteUserById: id={}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
