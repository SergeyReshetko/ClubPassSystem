package com.passsystem.exception;

import jakarta.persistence.EntityNotFoundException;

public class QrCodeNotFoundException extends EntityNotFoundException {
    public QrCodeNotFoundException(String message) {
        super(message);
    }
}
