package com.fabbo.todoapp.common.utils;

import com.fabbo.todoapp.common.clients.ObjectStorageClient;
import com.fabbo.todoapp.common.data.exceptions.RuntimeIOException;
import com.fabbo.todoapp.common.data.models.ApiImage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ImageUtils {

    public static InputStream getImageStreamWithoutMetadata(final MultipartFile imageFile) {
        try {
            return imageFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public static String generatePath(
            final String prefix,
            final String imageId,
            final MultipartFile imageFile
    ) {
        final String formatExtension = MediaType
                .IMAGE_PNG_VALUE
                .equalsIgnoreCase(
                        imageFile.getContentType()
                )
                ? ".png"
                : ".jpeg";
        return prefix + "/" + imageId + formatExtension;
    }

    public static void updateImageWithUrl(
            final ObjectStorageClient objectStorageClient,
            final ApiImage apiImage,
            final int signHourDuration,
            Function<ApiImage, Void> onImageUpdated
    ) {
        if (
                apiImage.getUrl() != null
                        && apiImage.getUrlExpiration() != null
                        && apiImage.getUrlExpiration()
                        .isAfter(LocalDateTime.now())
        ) {
            return;
        }

        final LocalDateTime urlExpiration = LocalDateTime
                .now()
                .plusHours(signHourDuration);
        final URL imageUrl = objectStorageClient.getObjectUrl(
                apiImage.getPath(),
                Duration.ofHours(
                        signHourDuration
                )
        );
        if (imageUrl != null) {
            apiImage.setImageUrl(
                    imageUrl,
                    urlExpiration
            );
            onImageUpdated.apply(apiImage);
        }
    }
}
