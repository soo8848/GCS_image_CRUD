package com.example.gcsimage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PutMapping
    public FileInfo uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    @GetMapping("/{id}")
    public FileInfo getFile(@PathVariable Long id) {
        return fileService.getFile(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return "Deleted file with ID: " + id;
    }
}
