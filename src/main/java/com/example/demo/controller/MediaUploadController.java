package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping({"/api/media"})
@RestController
public class MediaUploadController {

    @Value("${lab.security.enabled}")
    private boolean isSecurityEnabled;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/var/media/uploads/";
    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "mp3", "mp4");

    @PostMapping({"/upload"})
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        File targetFile;
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Vui lòng chọn một file để tải lên!");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return ResponseEntity.badRequest().body("Tên file không hợp lệ!");
        }
        if (this.isSecurityEnabled) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            if (!this.ALLOWED_EXTENSIONS.contains(fileExtension)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Định dạng file không được phép! Chỉ chấp nhận: " + String.valueOf(this.ALLOWED_EXTENSIONS));
            }
            String thuanTuyFilename = new File(originalFilename).getName();
            targetFile = new File(this.UPLOAD_DIR, thuanTuyFilename);
        } else {
            targetFile = new File(this.UPLOAD_DIR + originalFilename);
        }
        try {
            File parentDir = targetFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            file.transferTo(targetFile);
            return ResponseEntity.ok("Tải lên file thành công! Đường dẫn lưu trữ: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống khi lưu file: " + e.getMessage());
        }
    }
}