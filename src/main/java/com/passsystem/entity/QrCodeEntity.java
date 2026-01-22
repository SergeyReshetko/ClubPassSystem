package com.passsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code")
public class QrCodeEntity {
    
    @Id
    @Column(name = "qr_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(
            name = "qr_code",
            columnDefinition = "INT GENERATED ALWAYS AS IDENTITY (START WITH 100 INCREMENT BY 1) NOT NULL",
            unique = true,
            insertable = false,
            updatable = false
    )
    private Long qrCode;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @JsonBackReference("user-qrcode")
    private UserEntity userEntity;
}

