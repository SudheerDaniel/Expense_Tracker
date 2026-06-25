
package com.example.springbootmvcexample.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Service
public class SesService {

    private final SesClient sesClient;
    private final String fromEmail;
    private final String frontendUrl;

    public SesService(
            @Value("${ses.access.key}") String accessKey,
            @Value("${ses.secret.key}") String secretKey,
            @Value("${ses.region}") String region,
            @Value("${ses.from.email}") String fromEmail,
            @Value("${app.frontend.url}") String frontendUrl) {

        this.fromEmail = fromEmail;
        this.frontendUrl = frontendUrl;

        this.sesClient = SesClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public void sendVerificationEmail(String toEmail, String token) {
        String verificationLink = frontendUrl + "/verify-email?token=" + token;

        String htmlBody = """
                <html>
                <body>
                <h2>Verify your Expense Tracker account</h2>
                <p>Click the link below to verify your email address:</p>
                <a href="%s" style="background-color:#7c3aed;color:white;padding:10px 20px;text-decoration:none;border-radius:8px;">
                    Verify Email
                </a>
                <p>If you did not sign up, you can ignore this email.</p>
                <p>This link expires in 24 hours.</p>
                </body>
                </html>
                """.formatted(verificationLink);

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(toEmail).build())
                .message(Message.builder()
                        .subject(Content.builder().data("Verify your Expense Tracker account").build())
                        .body(Body.builder()
                                .html(Content.builder().data(htmlBody).build())
                                .build())
                        .build())
                .source(fromEmail)
                .build();

        sesClient.sendEmail(request);
    }
}




