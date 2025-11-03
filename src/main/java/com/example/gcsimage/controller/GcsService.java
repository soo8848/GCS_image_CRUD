package com.example.gcsimage.controller;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GcsService {

    private final String bucketName = "plantory";
    private final Storage storage = StorageOptions.getDefaultInstance().getService();

//    @PostConstruct
//    public void console() {
//        log.info("Storage Bean 생성 성공: {}", storage.getOptions().getProjectId());
//    }


    public String uploadFile(MultipartFile file) throws IOException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String blobName = String.format("images/%s/%s-%s",
                today, UUID.randomUUID(), file.getOriginalFilename());
//        String blobName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, blobName);
    }
}

//buket public 설정
//GCS 환경변수 설정 -> 이거하고 컴퓨터 한번 껏다 켜주기.
//.yml에 spring:cloud:gcp:storage:project-id: 설정
//
//
//간단한 테스트 사진올리기)
//포스트맨으로 Post Http 메소드 선택
//Body 탭 선택
//form-data 선택
//key-value 항목 추가:
//ㄴ 이때 반드시 키 이름은 file 이어야 된다.
//ㄴ Type도 셀렉트박스를 클릭해서 File타입으로 변경.

