package com.fabbo.todoapp.modules.user.infrastructure.output.clients;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.annotations.EnabledIfDocker;
import com.fabbo.todoapp.common.clients.ObjectStorageClient;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.common.config.S3Config;
import com.fabbo.todoapp.common.containers.InfraContainerTestParent;
import com.fabbo.todoapp.common.data.models.ApiImage;
import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.mappers.UserJpaMapper;
import com.fabbo.todoapp.modules.user.infrastructure.output.seeders.UserInfraTestingSeeder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

import static com.fabbo.todoapp.common.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(TodoappApplication.CONTAINER_TEST_PROFILE)
class UserImageClientTest extends InfraContainerTestParent {

    @Autowired
    private ObjectStorageClient objectStorageClient;

    @Autowired
    private UserImageClient userImageClient;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserInfraTestingSeeder userInfraTestingSeeder;

    @Autowired
    private S3Config s3Config;

    private static final UserJpaMapper USER_JPA_MAPPER = UserJpaMapper.INSTANCE;

    private ApiImage apiImage;

    private MultipartFile imageFile;

    @BeforeEach
    void setup() {
        final InputStream targetStream = new ByteArrayInputStream(
                randomText().getBytes()
        );
        apiImage = new ApiImage(
                randomUuid(),
                "/test/" + randomUuid()
        );
        imageFile = new MultipartFile() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getOriginalFilename() {
                return "";
            }

            @Override
            public String getContentType() {
                return "";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return targetStream;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                // Ignored
            }
        };
    }

    @Test
    void givenValidMultipartFile_whenUploadImage_shouldPutImage() {

        userImageClient.uploadImageContent(
                apiImage,
                imageFile
        );

        final URL imageUrl = objectStorageClient.getObjectUrl(
                apiImage.getPath(),
                Duration.ofMinutes(1),
                s3Config.getBucketName()
        );

        assertNotNull(imageUrl);
    }


    @Test
    @Transactional
    void givenValidUserImage_whenLoadImageUrl_shouldGetUrl() {
        final UserJpaEntity userJpaEntity = userInfraTestingSeeder
                .getUserJpaEntities()
                .get(
                        randomInt(
                                0,
                                userInfraTestingSeeder
                                        .getUserJpaEntities()
                                        .size()
                        )
                );
        final User user = USER_JPA_MAPPER.userEntityToAccount(
                userJpaEntity
        );

        apiImage = userImageRepository.save(
                apiImage,
                user.getId()
        );
        user.setImage(apiImage);

        userImageClient.loadUserImageUrl(
                user
        );

        assertNotNull(user.getImage().getUrl());
    }
}
