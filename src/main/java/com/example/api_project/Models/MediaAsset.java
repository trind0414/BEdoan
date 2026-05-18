package com.example.api_project.Models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media_assets")
@Data
public class MediaAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType; // image hoặc video

    @Column(name = "file_path", nullable = false, length = 666)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "is_public")
    private boolean isPublic = false;

    @Column(name = "file_size", nullable = false, length = 30)
    private Long fileSize;

    @Column(name = "created_at", nullable = false, length = 80)
    private LocalDateTime createdAt;
}
