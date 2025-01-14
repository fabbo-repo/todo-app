package com.fabbo.todoapp.modules.user.domain.services;

import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.application.repositories.UserRepository;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import com.fabbo.todoapp.modules.user.application.usecases.UpdateUserUseCase;
import com.fabbo.todoapp.modules.user.domain.data.exceptions.UserNotFoundException;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;
import com.fabbo.todoapp.modules.user.infrastructure.output.clients.UserImageClient;
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

    private final UserImageClient userImageClient;

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
        userImageClient.loadUserImageUrl(storedUser);

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
            userImageRepository.deleteByUserId(
                    storedUser.getId()
            );
            userImageClient.deleteImageContent(
                    storedUser.getImage()
            );
        }

        storedUser.update(updateUserDto);

        userRepository.save(storedUser);

        if (updateUserDto.getImageFile() != null) {
            userImageRepository.save(
                    storedUser.getImage(),
                    storedUser.getId()
            );
            userImageClient.uploadImageContent(
                    storedUser.getImage(),
                    updateUserDto.getImageFile()
            );
        }

        return null;
    }
}
