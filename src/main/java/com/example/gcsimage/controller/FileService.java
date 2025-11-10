package com.example.gcsimage.controller;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileInfoRepository fileInfoRepository;
    private final String bucketName = "plantory";
    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public FileInfo uploadFile(MultipartFile file) throws IOException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String blobName = String.format("images/%s/%s-%s", today,UUID.randomUUID(),file.getOriginalFilename());

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());



        String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

        FileInfo fileInfo = FileInfo.builder()
                .fileName(blobName)
                .fileUrl(fileUrl)
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .build();

        return fileInfoRepository.save(fileInfo);
    }

    public FileInfo getFile(Long id) {
        return fileInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다: " + id));
    }

    public void deleteFile(Long id) {
        FileInfo fileInfo = getFile(id);
        storage.delete(bucketName, fileInfo.getFileName());
        fileInfoRepository.deleteById(id);
    }
}
