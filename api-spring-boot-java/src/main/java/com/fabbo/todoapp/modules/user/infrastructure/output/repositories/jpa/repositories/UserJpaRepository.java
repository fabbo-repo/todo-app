package com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.repositories;

import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String>,
                                           JpaSpecificationExecutor<UserJpaEntity> {
}
