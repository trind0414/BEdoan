package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: MediaPreviewController.class */
@RequestMapping({"/api/media"})
@RestController
public class MediaPreviewController {

    @Value("${lab.security.enabled}")
    private boolean isSecurityEnabled;

    @GetMapping({"/preview"})
    public ResponseEntity<String> previewRemoteMedia(@RequestParam("url") String urlString) {
        if (this.isSecurityEnabled) {
            try {
                URI uri = new URI(urlString);
                String host = uri.getHost();
                if (host == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URL không hợp lệ!");
                }
                InetAddress inetAddress = InetAddress.getByName(host);
                if (inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isSiteLocalAddress()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Truy cập vào IP nội bộ bị nghiêm cấm để đảm bảo an toàn!");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể phân giải URL!");
            }
        }
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            while (true) {
                String inputLine = in.readLine();
                if (inputLine != null) {
                    content.append(inputLine);
                } else {
                    in.close();
                    connection.disconnect();
                    return ResponseEntity.ok("Nội dung phản hồi từ URL kết nối: \n" + content.toString());
                }
            }
        } catch (Exception e2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể kết nối tới URL yêu cầu: " + e2.getMessage());
        }
    }
}