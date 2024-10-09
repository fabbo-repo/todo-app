package com.fabbo.todoapp.modules.task.infrastructure.output.repositories.seeders;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntityFactory;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.repositories.TaskJpaRepository;
import com.fabbo.todoapp.modules.user.infrastructure.seeders.UserInfraTestingSeeder;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomInt;

@RequiredArgsConstructor
@Profile(TodoappApplication.CONTAINER_TEST_PROFILE)
@Component
@Slf4j
@Getter
public class TaskInfraTestingSeeder {

    private final UserInfraTestingSeeder userInfraTestingSeeder;

    private final TaskJpaRepository taskJpaRepository;

    private final List<TaskJpaEntity> taskJpaEntities = new ArrayList<>();

    public void insertData() {
        log.info("Initializing Task Infra Testing Seeder...");

        for (final UserJpaEntity userJpaEntity : userInfraTestingSeeder.getUserJpaEntities()) {
            final int randomTaskSizePerUser = randomInt(3, 10);

            for (int i = 0; i < randomTaskSizePerUser; i++) {
                final TaskJpaEntity taskJpaEntity = TaskJpaEntityFactory
                        .taskJpaEntity();
                taskJpaEntity.setOwner(userJpaEntity);

                final TaskJpaEntity storedTaskJpaEntity = taskJpaRepository
                        .save(taskJpaEntity);
                taskJpaEntities.add(storedTaskJpaEntity);
            }
        }

        log.info("Task Infra Testing Seeder initialized");
    }
}
