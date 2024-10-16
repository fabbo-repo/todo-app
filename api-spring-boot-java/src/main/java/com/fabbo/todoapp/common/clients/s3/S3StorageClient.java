package com.fabbo.todoapp.common.clients.s3;

import com.fabbo.todoapp.common.clients.ObjectStorageClient;
import com.fabbo.todoapp.common.config.S3Config;
import com.fabbo.todoapp.common.data.exceptions.RuntimeIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class S3StorageClient implements ObjectStorageClient {

    private final S3Config s3Config;

    @Override
    public void putObject(
            final InputStream objectStream,
            final String objectId,
            final String bucketName
    ) {
        try {
            final PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .key(objectId)
                    .bucket(bucketName)
                    .build();
            s3Config.getS3Client()
                    .putObject(
                            putObjectRequest,
                            RequestBody.fromInputStream(
                                    objectStream,
                                    objectStream.available()
                            )
                    );
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public URL getObjectUrl(
            final String objectId,
            final Duration signDuration,
            final String bucketName
    ) {
        final GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest
                .builder()
                .getObjectRequest(
                        builder ->
                                builder.key(objectId)
                                       .bucket(bucketName)
                )
                .signatureDuration(signDuration)
                .build();

        final PresignedGetObjectRequest presignedGetObjectRequest = s3Config
                .getS3Presigner()
                .presignGetObject(
                        getObjectPresignRequest
                );

        return presignedGetObjectRequest
                .url();
    }

    @Override
    public void deleteObject(
            final String objectId,
            final String bucketName
    ) {
        s3Config.getS3Client()
                .deleteObject(
                        DeleteObjectRequest
                                .builder()
                                .key(objectId)
                                .bucket(bucketName)
                                .build()
                );
    }

    @Override
    public boolean existsObject(
            final String objectId,
            final String bucketName
    ) {
        final GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .key(objectId)
                .bucket(bucketName)
                .build();
        try {
            s3Config
                    .getS3Client()
                    .getObject(
                            getObjectRequest
                    );
            return true;
        } catch (final S3Exception s3Exception) {
            return false;
        }
    }
}
