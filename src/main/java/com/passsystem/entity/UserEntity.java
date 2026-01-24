package com.passsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    
    @NonNull
    @Column(name = "last_Name", nullable = false, length = 20)
    private String lastName;
    
    @NonNull
    @Column(name = "middle_Name", nullable = false, length = 20)
    private String middleName;
    
    @OneToOne(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonManagedReference("user-qrcode")
    private QrCodeEntity qrCodeEntity;
}
