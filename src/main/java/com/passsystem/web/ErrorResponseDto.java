package com.passsystem.web;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String massage,
        String detailedMassage,
        LocalDateTime exceptionTime
) {
}
