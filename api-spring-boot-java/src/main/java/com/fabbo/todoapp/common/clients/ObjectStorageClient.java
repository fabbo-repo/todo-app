package com.fabbo.todoapp.common.clients;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

public interface ObjectStorageClient {
    void putObject(
            InputStream objectStream,
            String objectId,
            String bucketName
    );

    URL getObjectUrl(
            String objectId,
            Duration signDuration,
            String bucketName
    );

    void deleteObject(
            String objectId,
            String bucketName
    );

    boolean existsObject(
            String objectId,
            String bucketName
    );
}
