package com.example.api_project.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole name; // ROLE_USER, ROLE_ADMIN, ROLE_FILE_MANAGER...

    @Column(length = 255)
    private String description;
}
