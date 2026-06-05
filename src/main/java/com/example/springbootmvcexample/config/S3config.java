package com.example.springbootmvcexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key:dummy}") // Default value set to "dummy" for local development
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:dummy}") // Default value set to "dummy" for local development
    private String secretKey;

    @Value("${cloud.aws.region.static:us-east-1}") // Default value set to "us-east-1" for local development
    private String region;

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
}
