package com.fabbo.todoapp.modules.user.domain.services;

import com.fabbo.todoapp.common.clients.ObjectStorageClient;
import com.fabbo.todoapp.common.utils.ImageUtils;
import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.application.repositories.UserRepository;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import com.fabbo.todoapp.modules.user.application.usecases.UpdateUserUseCase;
import com.fabbo.todoapp.modules.user.domain.data.exceptions.UserNotFoundException;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements GetUserUseCase, UpdateUserUseCase {
    private final UserRepository userRepository;

    private final UserImageRepository userImageRepository;

    private final ObjectStorageClient objectStorageClient;

    @Override
    public User getUser(final GetUserProps getUserProps) {
        if (getUserProps.getToken() != null) {
            User.validateToken(getUserProps.getToken());
        }

        final Optional<User> optionalStoredUser = userRepository
                .findById(getUserProps.getId());
        final User storedUser = getUserProps.isCreateIfNotExists() ?
                                optionalStoredUser
                                        .orElseGet(
                                                () ->
                                                        userRepository
                                                                .save(new User(getUserProps.getToken()))
                                        ) :
                                optionalStoredUser.orElseThrow(
                                        UserNotFoundException::new
                                );
        loadUserImageUrl(storedUser);

        return storedUser;
    }

    @Override
    public Void updateUser(final UpdateUserProps updateUserDto) {
        final User storedUser = getUser(updateUserDto);

        // Stored image must be removed from object storage
        // service if a new image is being uploaded
        if (
                updateUserDto.getImageFile() != null
                && storedUser.getImage() != null
                && storedUser.getImage().getPath() != null
        ) {
            objectStorageClient.deleteObject(
                    storedUser.getImage().getPath()
            );
        }

        storedUser.update(updateUserDto);

        userRepository.save(storedUser);

        if (updateUserDto.getImageFile() != null) {
            userImageRepository.save(
                    storedUser.getImage(),
                    storedUser.getId()
            );
            objectStorageClient.putObject(
                    ImageUtils.getImageStreamWithoutMetadata(
                            updateUserDto.getImageFile()
                    ),
                    storedUser.getImage()
                              .getPath()
            );
        }

        return null;
    }

    private void loadUserImageUrl(final User user) {
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
}
