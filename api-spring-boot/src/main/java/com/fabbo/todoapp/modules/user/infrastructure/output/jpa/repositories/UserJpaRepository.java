package com.fabbo.todoapp.modules.user.infrastructure.output.jpa.repositories;

import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String>,
                                           JpaSpecificationExecutor<UserJpaEntity> {
}
