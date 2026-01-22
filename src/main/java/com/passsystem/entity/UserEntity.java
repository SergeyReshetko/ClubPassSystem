package com.passsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    
    @Column(name = "last_Name", nullable = false, length = 20)
    private String lastName;
    
    @Column(name = "middle_Name", nullable = false, length = 20)
    private String middleName;
    
    @OneToOne(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonManagedReference("user-qrcode")
    private QrCodeEntity qrCodeEntity;
}
