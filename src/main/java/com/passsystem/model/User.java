package com.passsystem.model;

import com.passsystem.entity.QrCodeEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record User(
    @Null
    Long id,
    @NotNull
    String firstName,
    @NotNull
    String lastName,
    String middleName,
    @NotNull
    QrCodeEntity qrCodeEntity
) {
}
