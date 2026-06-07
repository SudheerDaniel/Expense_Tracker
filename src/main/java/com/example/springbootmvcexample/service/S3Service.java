package com.example.springbootmvcexample.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
@Service
//@RequiredArgsConstructor
public class S3Service {
   
    private final S3Client s3Client;
    private final String BUCKET_NAME;

    public S3Service(S3Client s3Client, @Value("${aws.bucket.name:dummy}") String bucketName) {
        this.s3Client = s3Client;
        this.BUCKET_NAME = bucketName;
    }
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build(),
            RequestBody.fromBytes(file.getBytes())
        );
        return "File uploaded: " + fileName;
    }

    public String downloadFile(String key, String downloadPath) {
        s3Client.getObject(
            GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build(),
            Paths.get(downloadPath));
        return "Downloaded to: " + downloadPath;
    }

    public List<String> listFiles() {
        return s3Client.listObjectsV2(
            ListObjectsV2Request.builder()
                .bucket(BUCKET_NAME)
                .build()
        ).contents().stream()
            .map(S3Object::key)
            .collect(Collectors.toList());
    }

    public String deleteFile(String key) {
        s3Client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build()
        );
        return "File deleted: " + key;
    }



}
