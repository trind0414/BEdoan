package com.example.api_project.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media_shares")
@Data
public class MediaShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // File nào được chia sẻ
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", nullable = false)
    private MediaAsset media;

    // Chia sẻ cho User nào
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with_user_id", nullable = false)
    private User sharedWith;
}
