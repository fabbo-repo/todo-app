package com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.entities;

import com.fabbo.todoapp.common.jpa.entities.AuditableJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserJpaEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = TaskJpaEntity.TABLE_NAME)
public class TaskJpaEntity extends AuditableJpaEntity {
    public static final String TABLE_NAME = "tasks";

    public static final String ID_COL = "id";
    public static final String TITLE_COL = "title";
    public static final String DESCRIPTION_COL = "description";
    public static final String IS_FINISHED_COL = "is_finished";
    public static final String DEADLINE_COL = "deadline";
    public static final String OWNER_ID_COL = "owner_id";

    public static final String IS_FINISHED_FIELD = "isFinished";
    public static final String DEADLINE_FIELD = "deadline";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ID_COL)
    private UUID id;

    @Column(name = TITLE_COL, nullable = false, length = 50)
    private String title;

    @Column(name = DESCRIPTION_COL, length = 300)
    private String description;

    @Column(name = IS_FINISHED_COL, nullable = false)
    private boolean isFinished;

    @Column(name = DEADLINE_COL)
    private LocalDateTime deadline;

    @JoinColumn(name = OWNER_ID_COL, nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private UserJpaEntity owner;
}
