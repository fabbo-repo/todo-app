package com.fabbo.todoapp.common.clients;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

public interface ObjectStorageClient {
    void putObject(
            InputStream objectStream,
            String objectId
    );

    URL getObjectUrl(
            String objectId,
            Duration signDuration
    );

    void deleteObject(String objectId);

    boolean existsObject(String objectId);
}
