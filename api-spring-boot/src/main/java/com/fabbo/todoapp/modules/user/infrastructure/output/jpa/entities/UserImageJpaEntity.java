package com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities;

import com.fabbo.todoapp.common.jpa.entities.AuditableJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UserImageJpaEntity.TABLE_NAME)
public class UserImageJpaEntity extends AuditableJpaEntity {
    public static final String TABLE_NAME = "user_images";

    public static final String ID_COL = "id";
    public static final String USER_ID_COL = "user_id";
    public static final String PATH_COL = "path";
    public static final String URL_COL = "url";
    public static final String URL_EXPIRATION_COL = "url_expiration";

    public static final String USER_FIELD = "user";

    @Id
    @Column(name = ID_COL)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = USER_ID_COL, nullable = false, unique = true)
    private UserJpaEntity user;

    @Column(name = PATH_COL, nullable = false)
    private String path;

    @Column(name = URL_COL)
    private String url;

    @Column(name = URL_EXPIRATION_COL)
    private LocalDateTime urlExpiration;
}
