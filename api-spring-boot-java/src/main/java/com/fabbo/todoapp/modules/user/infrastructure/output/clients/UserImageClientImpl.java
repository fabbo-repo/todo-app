package com.fabbo.todoapp.modules.user.infrastructure.output.clients;

import com.fabbo.todoapp.common.clients.ObjectStorageClient;
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

    @Override
    public void uploadImageContent(User user, MultipartFile image) {
        objectStorageClient.putObject(
                ImageUtils.getImageStreamWithoutMetadata(
                        image
                ),
                user.getImage()
                        .getPath()
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
                                            user.getImage(),
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
                image.getPath()
        );
    }
}
