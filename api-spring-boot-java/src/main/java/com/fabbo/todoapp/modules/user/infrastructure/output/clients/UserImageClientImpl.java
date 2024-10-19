package com.fabbo.todoapp.modules.user.infrastructure.output.clients;

import com.fabbo.todoapp.common.clients.ObjectStorageClient;
import com.fabbo.todoapp.common.config.S3Config;
import com.fabbo.todoapp.common.data.models.ApiImage;
import com.fabbo.todoapp.common.utils.ImageUtils;
import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UserImageClientImpl implements UserImageClient {
    private final ObjectStorageClient objectStorageClient;

    private final UserImageRepository userImageRepository;

    private final S3Config s3Config;

    @Override
    public void uploadImageContent(
            final ApiImage apiImage,
            final MultipartFile image
    ) {
        objectStorageClient.putObject(
                ImageUtils.getImageStreamWithoutMetadata(
                        image
                ),
                apiImage.getPath(),
                s3Config.getBucketName()
        );
    }

    @Override
    public void loadUserImageUrl(final User user) {
        user.setImage(
                userImageRepository
                        .findByUserId(user.getId())
                        .map(
                                apiImage -> {
                                    ImageUtils.updateImageWithUrl(
                                            objectStorageClient,
                                            s3Config.getBucketName(),
                                            apiImage,
                                            1,
                                            newApiImage -> {
                                                userImageRepository.save(
                                                        newApiImage,
                                                        user.getId()
                                                );
                                                return null;
                                            }
                                    );
                                    return apiImage;
                                }
                        )
                        .orElse(null)
        );
    }

    @Override
    public void deleteImageContent(final ApiImage image) {
        objectStorageClient.deleteObject(
                image.getPath(),
                s3Config.getBucketName()
        );
    }
}
