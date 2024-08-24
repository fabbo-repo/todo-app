package com.fabbo.todoapp.common.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class S3Config {

    @Value("${api.s3.bucket-name}")
    public String bucketName;

    @Value("${api.s3.region}")
    public String regionName;

    @Value("${api.s3.url}")
    private String url;

    @Value("${api.s3.access-key}")
    private String accessKey;

    @Value("${api.s3.secret-key}")
    private String secretKey;

    private AmazonS3 amazonS3;

    @PostConstruct
    public void setup() {
        amazonS3 = AmazonS3ClientBuilder
                .standard()
                .enablePathStyleAccess()
                .withEndpointConfiguration(getS3Endpoint())
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                getS3Credentials()
                        )
                )
                .build();
    }

    @SuppressWarnings({"squid:S6263"})
    public AWSCredentials getS3Credentials() {
        return new BasicAWSCredentials(
                accessKey,
                secretKey
        );
    }

    private AwsClientBuilder.EndpointConfiguration getS3Endpoint() {
        return new AwsClientBuilder.EndpointConfiguration(
                url,
                regionName
        );
    }
}
