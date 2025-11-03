package com.example.gcsimage.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final GcsService gcsService;

    public UploadController(GcsService gcsService) {
        this.gcsService = gcsService;
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        return gcsService.uploadFile(file);
    }
}
