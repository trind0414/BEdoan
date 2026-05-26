package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaStreamController {

    @Value("${lab.security.enabled}")
    private boolean isSecurityEnabled;
    private final String BASE_MEDIA_DIR = "var/media/";
    private final String UPLOAD_MEDIA_DIR = "var/media/uploads/";

    @GetMapping({"/api/media/stream"})
    public ResponseEntity<Resource> streamMedia(@RequestParam("file") String fileName) {
        File file;
        String contentType;
        if (this.isSecurityEnabled) {
            try {
                File requestedFile = new File("var/media/", fileName);
                String canonicalPath = requestedFile.getCanonicalPath();
                String baseCanonicalPath = new File("var/media/").getCanonicalPath();
                if (!canonicalPath.startsWith(baseCanonicalPath)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                file = requestedFile;
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            file = new File("var/media/" + fileName);
        }
        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }
        try {
            contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e2) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().header("Content-Type", new String[]{contentType}).body(new FileSystemResource(file));
    }

    @GetMapping({"/api/media/stream/uploads"})
    public ResponseEntity<Resource> streamMediaUpload(@RequestParam("file") String fileName) {
        File file;
        String contentType;
        if (this.isSecurityEnabled) {
            try {
                File requestedFile = new File("var/media/uploads/", fileName);
                String canonicalPath = requestedFile.getCanonicalPath();
                String baseCanonicalPath = new File("var/media/uploads/").getCanonicalPath();
                if (!canonicalPath.startsWith(baseCanonicalPath)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                file = requestedFile;
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            file = new File("var/media/uploads/" + fileName);
        }
        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }
        try {
            contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e2) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().header("Content-Type", new String[]{contentType}).body(new FileSystemResource(file));
    }
}