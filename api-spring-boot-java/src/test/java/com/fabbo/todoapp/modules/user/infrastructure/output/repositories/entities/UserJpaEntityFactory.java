package com.fabbo.todoapp.modules.user.infrastructure.output.repositories.entities;

import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomNullableText;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJpaEntityFactory {
    public static UserJpaEntity userJpaEntity() {
        return new UserJpaEntity(
                randomText(200),
                randomText(20),
                randomNullableText(300),
                randomNullableText(5)
        );
    }
}
