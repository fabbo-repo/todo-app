package com.fabbo.todoapp.modules.user.infrasructure.seeders;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.modules.user.infrasructure.persistence.entities.UserJpaEntityFactory;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.repositories.UserJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;

@RequiredArgsConstructor
@Profile(TodoappApplication.CONTAINER_TEST_PROFILE)
@Component
@Slf4j
@Getter
public class UserInfraTestingSeeder {

    private final List<UserJpaEntity> userJpaEntities = new ArrayList<>();

    private final UserJpaRepository userJpaRepository;

    private final String testPassword = randomText();

    public void insertData() {
        log.info("Creating User Infra Testing Data...");

        for (int i = 0; i < 3; i++) {
            final UserJpaEntity userJpaEntity = UserJpaEntityFactory
                    .userJpaEntity();

            final UserJpaEntity storedUserJpaEntity = userJpaRepository
                    .save(userJpaEntity);
            userJpaEntities.add(storedUserJpaEntity);
        }

        log.info("User Infra Testing Data created");
    }
}
