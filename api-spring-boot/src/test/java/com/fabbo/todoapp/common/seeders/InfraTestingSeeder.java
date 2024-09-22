package com.fabbo.todoapp.common.seeders;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.modules.task.infrasructure.output.persistence.seeders.TaskInfraTestingSeeder;
import com.fabbo.todoapp.modules.user.infrasructure.seeders.UserInfraTestingSeeder;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserJpaEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile(TodoappApplication.CONTAINER_TEST_PROFILE)
@Component
@RequiredArgsConstructor
@Slf4j
public class InfraTestingSeeder {
    private final UserInfraTestingSeeder userInfraTestingSeeder;

    private final TaskInfraTestingSeeder taskInfraTestingSeeder;

    @PostConstruct
    private void onStartup() {
        log.info("Initializing Infra Testing Data Seeder...");

        userInfraTestingSeeder.insertData();
        final List<UserJpaEntity> userJpaEntities
                = userInfraTestingSeeder.getUserJpaEntities();

        if (!userJpaEntities.isEmpty()) {
            taskInfraTestingSeeder.insertData();
        }

        log.info("Infra Testing Data Seeder initialized");
    }
}
