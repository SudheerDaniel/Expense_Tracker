package com.example.springbootmvcexample.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
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
    private final String minioPublicEndpoint; // public, for URL returned to browser
    public S3Service(S3Client s3Client, @Value("${aws.bucket.name:dummy-bucket}") String bucketName, @Value("${minio.public.endpoint:http://localhost:9000}")String minioPublicEndpoint) {
        this.s3Client = s3Client;
        this.BUCKET_NAME = bucketName; // Default value set to "dummy-bucket" for local development, replace with actual bucket name in production
        this.minioPublicEndpoint = minioPublicEndpoint;
    }
    public String uploadFile(MultipartFile file) throws IOException {
       // generate unique filename to avoid overwriting existing files 
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")? originalFilename.substring(originalFilename.lastIndexOf("."))
        : "";
        String fileName = UUID.randomUUID().toString() + extension;

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .contentType(file.getContentType())
                .build(),
            RequestBody.fromBytes(file.getBytes())
        );
        // return the full URL so frontend can store it in receiptUrl field
        return minioPublicEndpoint + "/" + BUCKET_NAME + "/" + fileName;
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
