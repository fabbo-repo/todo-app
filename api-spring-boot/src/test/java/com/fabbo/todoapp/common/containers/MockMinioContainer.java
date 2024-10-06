package com.fabbo.todoapp.common.containers;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.GenericContainer;

public class MockMinioContainer extends GenericContainer<MockMinioContainer> {
    private static final String IMAGE_VERSION = "bitnami/minio:2024.8.17";

    private static final int MINIO_BIND_PORT = 9000;
    private static final int MINIO_EXPOSED_PORT = 9000;

    private static final String MINIO_ACCESS_KEY = "accessKey";
    private static final String MINIO_SECRET_KEY = "secretKey";

    private static MockMinioContainer container;

    private MockMinioContainer() {
        super(IMAGE_VERSION);
    }

    public static MockMinioContainer getInstance() {
        if (container == null) {
            container = new MockMinioContainer()
                    .withExposedPorts(MINIO_EXPOSED_PORT)
                    .withEnv("MINIO_ROOT_USER", MINIO_ACCESS_KEY)
                    .withEnv("MINIO_ROOT_PASSWORD", MINIO_SECRET_KEY)
                    .withCreateContainerCmdModifier(
                            cmd -> cmd.withHostConfig(
                                    new HostConfig()
                                            .withPortBindings(
                                                    new PortBinding(
                                                            Ports.Binding.bindPort(
                                                                    MINIO_BIND_PORT
                                                            ),
                                                            new ExposedPort(
                                                                    MINIO_EXPOSED_PORT
                                                            )
                                                    )
                                            )
                            )
                    );
        }
        return container;
    }

    // Next both methods are necessary to run multiple tests
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        // Do nothing, JVM handles shut down
    }
}
