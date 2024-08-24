package com.fabbo.todoapp.common.clients;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public interface ObjectStorageClient {
    void putObject(
            InputStream objectStream,
            String objectId
    );

    URL getObjectUrl(
            String objectId,
            int signDuration,
            TimeUnit signDurationTimeUnit
    );

    void deleteObject(String objectId);

    boolean existsObject(String objectId);
}
