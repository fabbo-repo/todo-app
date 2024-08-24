package com.fabbo.todoapp.modules.user.infrastructure.output.jpa.repositories;

import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserImageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserImageJpaRepository extends JpaRepository<UserImageJpaEntity, UUID>,
                                                JpaSpecificationExecutor<UserImageJpaEntity> {
    Optional<UserImageJpaEntity> findByUserId(String userId);
}
