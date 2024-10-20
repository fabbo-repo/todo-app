package com.fabbo.todoapp.modules.task.infrastructure.output.repositories;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.annotations.EnabledIfDocker;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.common.containers.InfraContainerTestParent;
import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.common.data.props.ApiPageProps;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.FilterTasksProps;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntityFactory;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.mappers.TaskJpaMapper;
import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.seeders.TaskInfraTestingSeeder;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.seeders.UserInfraTestingSeeder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.*;

import static com.fabbo.todoapp.common.utils.TestDataUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(TodoappApplication.CONTAINER_TEST_PROFILE)
class TaskRepositoryTest extends InfraContainerTestParent {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserInfraTestingSeeder userInfraTestingSeeder;

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

    @Test
    @Transactional
    void givenInvalidTaskId_whenFindById_shouldReturnNoTask() {

        final UUID taskId = randomUuid();

        final Optional<Task> result = taskRepository.findById(
                taskId
        );

        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    void givenValidFilter_whenFindAll_shouldReturnTaskPage() {
        LocalDateTime minDeadline = LocalDateTime.now();
        final boolean expectedIsFinished = randomBool();

        final List<Task> expectedTasks = new ArrayList<>();

        for (final TaskJpaEntity taskJpaEntity : taskInfraTestingSeeder.getTaskJpaEntities()) {
            if (taskJpaEntity.getDeadline().isBefore(minDeadline)) {
                minDeadline = taskJpaEntity.getDeadline();
            }
            if (taskJpaEntity.isFinished() == expectedIsFinished) {
                expectedTasks.add(
                        TASK_JPA_MAPPER.entityToTask(
                                taskJpaEntity
                        )
                );
            }
        }

        expectedTasks.sort(
                Comparator.comparing(
                        o -> o.getAuditableData().getCreatedAt()
                )
        );
        final ApiPage<Task> expectedTaskPage = singleApiPage(expectedTasks);

        final FilterTasksProps filter = new FilterTasksProps();
        filter.setDeadlineGte(minDeadline);
        filter.setIsFinished(expectedIsFinished);

        final ApiPage<Task> result = taskRepository.findAll(
                filter,
                new ApiPageProps(
                        0,
                        expectedTasks.size()
                )
        );

        assertThat(expectedTaskPage)
                .usingRecursiveComparison()
                .ignoringFields("results")
                .isEqualTo(result);
    }

    @Test
    @Transactional
    void givenValidTaskId_whenExistsById_shouldReturnTrue() {
        final int randomIndex = randomInt(
                0,
                taskInfraTestingSeeder.getTaskJpaEntities().size() - 1
        );
        final TaskJpaEntity taskJpaEntity = taskInfraTestingSeeder
                .getTaskJpaEntities()
                .get(randomIndex);
        final UUID taskId = taskJpaEntity.getId();

        final boolean result = taskRepository.existsById(
                taskId
        );

        assertTrue(result);
    }

    @Test
    @Transactional
    void givenInvalidTaskId_whenExistsById_shouldReturnFalse() {
        final UUID taskId = randomUuid();

        final boolean result = taskRepository.existsById(
                taskId
        );

        assertFalse(result);
    }

    @Test
    @Transactional
    void givenValidTask_whenSave_shouldTask() {
        final int userRandomIndex = randomInt(
                0,
                userInfraTestingSeeder.getUserJpaEntities().size() - 1
        );
        final UserJpaEntity userJpaEntity = userInfraTestingSeeder
                .getUserJpaEntities()
                .get(userRandomIndex);

        final TaskJpaEntity taskJpaEntity = TaskJpaEntityFactory
                .taskJpaEntity();
        taskJpaEntity.setOwner(userJpaEntity);
        final Task expectedTask = TASK_JPA_MAPPER.entityToTask(
                taskJpaEntity
        );

        final Task result = taskRepository.save(expectedTask);

        assertNotNull(result.getId());
        assertThat(expectedTask)
                .usingRecursiveComparison()
                .ignoringFields("id", "auditableData")
                .isEqualTo(result);
    }

    @Test
    @Transactional
    void givenValidTaskId_whenDeleteById_shouldDeleteTask() {
        final int randomIndex = randomInt(
                0,
                taskInfraTestingSeeder.getTaskJpaEntities().size() - 1
        );
        final TaskJpaEntity taskJpaEntity = taskInfraTestingSeeder
                .getTaskJpaEntities()
                .get(randomIndex);
        final UUID taskId = taskJpaEntity.getId();

        taskRepository.deleteById(taskId);

        final boolean exists = taskRepository.existsById(taskId);

        assertFalse(exists);
    }
}
