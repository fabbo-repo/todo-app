package com.fabbo.todoapp.modules.task.infrastructure.output.persistence;

import com.fabbo.todoapp.common.data.mappers.ApiPageMapper;
import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.common.data.props.ApiPageProps;
import com.fabbo.todoapp.common.jpa.entities.AuditableJpaEntity;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.FilterTasksProps;
import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.entities.TaskJpaEntity;
import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.mappers.TaskJpaMapper;
import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.repositories.TaskJpaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final TaskJpaRepository taskJpaRepository;

    private static final TaskJpaMapper TASK_JPA_MAPPER = TaskJpaMapper.INSTANCE;

    private static final ApiPageMapper<TaskJpaEntity> TASK_JPA_ENTITY_API_PAGE_MAPPER = new ApiPageMapper<>();

    @Override
    public Optional<Task> findById(final UUID id) {
        return taskJpaRepository
                .findById(id)
                .map(TASK_JPA_MAPPER::entityToTask);
    }

    @Override
    public ApiPage<Task> findAll(
            final FilterTasksProps filter,
            final ApiPageProps pageProps
    ) {
        final Specification<TaskJpaEntity> specification = (
                @NonNull final Root<TaskJpaEntity> root,
                final CriteriaQuery<?> query,
                @NonNull final CriteriaBuilder criteriaBuilder
        ) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (filter.getIsFinished() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get(TaskJpaEntity.IS_FINISHED_FIELD),
                                filter.getIsFinished()
                        )
                );
            }
            if (filter.getDeadlineGte() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(TaskJpaEntity.DEADLINE_FIELD),
                                filter.getDeadlineGte()
                        )
                );
            }
            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };

        final Pageable pageable = PageRequest.of(
                pageProps.getPageNum(),
                pageProps.getPageSize(),
                Sort.by(
                        AuditableJpaEntity.CREATED_AT_FIELD
                ).descending()
        );

        final ApiPage<TaskJpaEntity> taskJpaEntityApiPage = TASK_JPA_ENTITY_API_PAGE_MAPPER
                .entityToDto(
                        taskJpaRepository
                                .findAll(
                                        specification,
                                        pageable
                                )
                );
        return taskJpaEntityApiPage
                .map(TASK_JPA_MAPPER::entityToTask);
    }

    @Override
    public boolean existsById(final UUID id) {
        return taskJpaRepository.existsById(id);
    }

    @Override
    public Task save(final Task task) {
        final TaskJpaEntity taskJpaEntity = TASK_JPA_MAPPER.taskToEntity(task);
        return TASK_JPA_MAPPER.entityToTask(
                taskJpaRepository.save(taskJpaEntity)
        );
    }

    @Override
    public void deleteById(final UUID id) {
        taskJpaRepository.deleteById(id);
    }
}
