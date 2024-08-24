package com.fabbo.todoapp.common.clients.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fabbo.todoapp.common.clients.ObjectStorageClient;
import com.fabbo.todoapp.common.config.S3Config;
import com.fabbo.todoapp.common.data.exceptions.RuntimeIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class S3StorageClient implements ObjectStorageClient {

    private final S3Config s3Config;

    @Override
    public void putObject(
            final InputStream objectStream,
            final String objectId
    ) {
        try {
            final ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(objectStream.available());
            s3Config.getAmazonS3()
                    .putObject(
                            s3Config.bucketName,
                            objectId,
                            objectStream,
                            meta
                    );
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public URL getObjectUrl(
            final String objectId,
            final int signDuration,
            final TimeUnit signDurationTimeUnit
    ) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(
                convertTimeUnitToCalendarField(
                        signDurationTimeUnit
                ),
                signDuration
        );
        return s3Config
                .getAmazonS3()
                .generatePresignedUrl(
                        s3Config.bucketName,
                        objectId,
                        calendar.getTime()
                );
    }

    @Override
    public void deleteObject(final String objectId) {
        s3Config.getAmazonS3()
                .deleteObject(
                        s3Config.bucketName,
                        objectId
                );
    }

    @Override
    public boolean existsObject(final String objectId) {
        return s3Config.getAmazonS3()
                       .doesObjectExist(
                               s3Config.bucketName,
                               objectId
                       );
    }

    public static int convertTimeUnitToCalendarField(
            final TimeUnit timeUnit
    ) {
        return switch (timeUnit) {
            case SECONDS -> Calendar.SECOND;
            case MINUTES -> Calendar.MINUTE;
            case HOURS -> Calendar.HOUR;
            case DAYS -> Calendar.DAY_OF_YEAR;
            default -> throw new IllegalArgumentException();
        };
    }
}
