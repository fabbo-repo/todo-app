package com.fabbo.todoapp.common.containers;

import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Random;

public class IntegrationContainerTestParent {
    public static final int MINIO_PORT = randomValidPort();

    @Container
    protected static final PostgreSQLContainer<?> postgreSqlContainer = MockPostgreSqlContainer.getInstance();

    @Container
    protected static final GenericContainer<?> minioContainer = MockMinioContainer
            .getInstance()
            .withCreateContainerCmdModifier(cmd -> cmd
                    .withHostConfig(
                            HostConfig.newHostConfig()
                                      .withPortBindings(
                                              PortBinding.parse(
                                                      MINIO_PORT +
                                                      ":9000")
                                      )
                    )
            );

    @DynamicPropertySource
    static void registerSettings(DynamicPropertyRegistry registry) {
        postgreSqlContainer.start();

        registry.add(
                "spring.datasource.url",
                postgreSqlContainer::getJdbcUrl
        );
        registry.add(
                "api.s3.url",
                () -> "http://localhost:" + MINIO_PORT
        );
    }

    public static int randomValidPort() {
        return new Random().nextInt(32001 - 30000) + 30000;
    }
}
