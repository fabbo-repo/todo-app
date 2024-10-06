package com.fabbo.todoapp.modules.task.infrasructure.output.persistence;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.annotations.EnabledIfDocker;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.common.containers.InfraContainerTestParent;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.infrasructure.output.persistence.seeders.TaskInfraTestingSeeder;
import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.entities.TaskJpaEntity;
import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.mappers.TaskJpaMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomInt;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(TodoappApplication.CONTAINER_TEST_PROFILE)
class TaskRepositoryTest extends InfraContainerTestParent {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskInfraTestingSeeder taskInfraTestingSeeder;

    private static final TaskJpaMapper TASK_JPA_MAPPER = TaskJpaMapper.INSTANCE;

    @Test
    @Transactional
    void givenValidTaskId_whenFindById_shouldReturnTask() {
        final int randomIndex = randomInt(
                0,
                taskInfraTestingSeeder.getTaskJpaEntities().size() - 1
        );
        final TaskJpaEntity taskJpaEntity = taskInfraTestingSeeder
                .getTaskJpaEntities()
                .get(randomIndex);
        final Task expectedTask = TASK_JPA_MAPPER.entityToTask(
                taskJpaEntity
        );
        final UUID taskId = taskJpaEntity.getId();

        final Optional<Task> result = taskRepository.findById(
                taskId
        );

        assertTrue(result.isPresent());
        assertThat(expectedTask)
                .usingRecursiveComparison()
                .ignoringFields("auditableData")
                .isEqualTo(result.get());
    }

}
