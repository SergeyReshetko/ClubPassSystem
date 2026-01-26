package com.passsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@Component
public class QrCodeDto {
    @NotNull
    private Long id;
    @NotNull
    private UUID qrCode;
}
