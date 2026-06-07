package com.example.springbootmvcexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.net.URI;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;



@Configuration
public class S3Config {

<<<<<<< HEAD:src/main/java/com/example/springbootmvcexample/config/S3config.java
    @Value("${cloud.aws.credentials.access-key:dummy}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:dummy}")
    private String secretKey;

    @Value("${cloud.aws.region.static:us-east-1}")
=======
    @Value("${cloud.aws.credentials.access-key:dummy}") // Default value set to "dummy" for local development
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:dummy}") // Default value set to "dummy" for local development
    private String secretKey;

    @Value("${cloud.aws.region.static:us-east-1}") // Default value set to "us-east-1" for local development
>>>>>>> origin/main:src/main/java/com/example/springbootmvcexample/config/S3Config.java
    private String region;

    @Value("${minio.endpoint:http://localhost:9000}")
    private String minioEndpoint;

    @Bean("s3Client")
    @Profile("local")
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
    @Bean("s3Client")
    @Profile("dev")
    public S3Client s3ClientDev() {

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean("s3Client")
    @Profile("docker")
    public S3Client s3ClientDocker() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .forcePathStyle(true) // Enable path-style access for localstack
                .endpointOverride(URI.create(minioEndpoint)) // LocalStack endpoint
                .build();
    }
}
