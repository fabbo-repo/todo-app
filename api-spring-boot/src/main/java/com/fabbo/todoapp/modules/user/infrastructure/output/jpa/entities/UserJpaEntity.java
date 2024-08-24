package com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities;

import com.fabbo.todoapp.common.jpa.entities.AuditableJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UserJpaEntity.TABLE_NAME)
public class UserJpaEntity extends AuditableJpaEntity {
    public static final String TABLE_NAME = "users";

    public static final String ID_COL = "id";
    private static final String USERNAME_COL = "username";
    private static final String DESCRIPTION_COL = "description";
    private static final String LOCALE_COL = "locale";

    public static final String ID_FIELD = "id";

    @Id
    @Column(name = ID_COL, length = 200)
    private String id;

    @Column(name = USERNAME_COL, length = 20)
    private String username;

    @Column(name = DESCRIPTION_COL, length = 300)
    private String description;

    @Column(name = LOCALE_COL, length = 5)
    private String locale;
}
