package com.fabbo.todoapp.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.endpoints.Endpoint;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Configuration
@Getter
public class S3Config {

    @Value("${api.s3.bucket-name}")
    private String bucketName;

    @Value("${api.s3.region}")
    private String regionName;

    @Value("${api.s3.url}")
    private String url;

    @Value("${api.s3.access-key}")
    private String accessKey;

    @Value("${api.s3.secret-key}")
    private String secretKey;

    private S3Client s3Client;

    private S3Presigner s3Presigner;

    @PostConstruct
    public void setup() {
        s3Client = S3Client
                .builder()
                .forcePathStyle(true)
                .endpointProvider(
                        endpointParams -> CompletableFuture
                                .completedFuture(
                                        getS3Endpoint()
                                )
                )
                .credentialsProvider(
                        getS3CredentialsProvider()
                )
                .region(Region.of(regionName))
                .build();
        s3Presigner = S3Presigner
                .builder()
                .s3Client(s3Client)
                .credentialsProvider(
                        getS3CredentialsProvider()
                )
                .endpointOverride(getS3Endpoint().url())
                .region(Region.of(regionName))
                .build();
    }

    @SuppressWarnings({"squid:S6263"})
    public AwsCredentialsProvider getS3CredentialsProvider() {
        return () -> AwsBasicCredentials.create(
                accessKey,
                secretKey
        );
    }

    private Endpoint getS3Endpoint() {
        return Endpoint
                .builder()
                .url(URI.create(
                        url + "/" + bucketName
                ))
                .build();
    }
}
