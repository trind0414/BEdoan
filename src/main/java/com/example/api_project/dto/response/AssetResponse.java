package com.example.api_project.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String ownerName;
    private LocalDateTime createdAt;
    private String url;
}
