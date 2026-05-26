package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* JADX INFO: loaded from: ViewController.class */
@Controller
public class ViewController {

    @Value("${lab.security.enabled}")
    private boolean isSecurityEnabled;
    private final String BASE_MEDIA_DIR = "var/media/";

    @GetMapping({"/"})
    public String index(Model model) {
        File[] files;
        File folder = new File("var/media/");
        List<String> mediaFiles = new ArrayList<>();
        if (folder.exists() && folder.isDirectory() && (files = folder.listFiles()) != null) {
            for (File file : files) {
                if (file.isFile()) {
                    mediaFiles.add(file.getName());
                }
            }
        }
        model.addAttribute("mediaFiles", mediaFiles);
        model.addAttribute("isSecuritySecure", Boolean.valueOf(this.isSecurityEnabled));
        return "index";
    }

    @GetMapping({"/studio"})
    public String studio(Model model) {
        model.addAttribute("isSecuritySecure", Boolean.valueOf(this.isSecurityEnabled));
        return "studio";
    }

    @GetMapping({"/login"})
    public String login(Model model) {
        model.addAttribute("isSecuritySecure", Boolean.valueOf(this.isSecurityEnabled));
        return "login";
    }

    @GetMapping({"/upload"})
    public String upload(Model model) {
        model.addAttribute("isSecuritySecure", Boolean.valueOf(this.isSecurityEnabled));
        List<String> filesInMediaDir = new ArrayList<>();
        File folder = new File("var/media/uploads/");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        filesInMediaDir.add(f.getName());
                    }
                }
            }
        } else {
            System.err.println("Warning: Media display path does not exist or is not a right directory");
        }
        model.addAttribute("filesInMediaDir", filesInMediaDir);
        return "upload";
    }
}