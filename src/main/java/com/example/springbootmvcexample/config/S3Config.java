package com.example.springbootmvcexample.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key:dummy}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:dummy}")
    private String secretKey;

    @Value("${cloud.aws.region.static:us-east-1}")
    private String region;

    @Value("${minio.endpoint:http://localhost:9000}")
    private String minioEndpoint;
  
    @Value("${minio.public.endpoint:http://localhost:9000}")
    private String minioPublicEndpoint;

    // --- local profile: MinIO (Docker Compose, local dev) ---
    @Bean("s3Client")
    @Profile("local")
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(minioEndpoint))
                .forcePathStyle(true)
                .build();
    }


    // --- dev profile: real AWS S3, default credential chain ---
    @Bean("s3Client")
    @Profile("dev")
    public S3Client s3ClientDev() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }


    // --- docker profile: Railway production, real AWS S3 ---
    @Bean("s3Client")
    @Profile("docker")
    public S3Client s3ClientDocker() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }


    @Bean("s3Presigner")
    @Profile("docker")
    public S3Presigner s3PresignerDocker() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
    
    @Bean("s3Presigner")
    @Profile("local")
    public S3Presigner s3PresignerLocal() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(minioEndpoint))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
     }

     @Bean("s3Presigner")
     @Profile("dev")
     public S3Presigner s3PresignerDev() {
         return S3Presigner.builder()
                 .region(Region.of(region))
                 .credentialsProvider(DefaultCredentialsProvider.create())
                 .build();
     }
    
}
