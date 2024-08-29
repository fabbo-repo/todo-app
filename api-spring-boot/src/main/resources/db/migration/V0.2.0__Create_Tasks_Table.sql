CREATE TABLE tasks (
  id UUID NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   created_by VARCHAR(200),
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   updated_by VARCHAR(200),
   title VARCHAR(50) NOT NULL,
   description VARCHAR(300),
   is_finished BOOLEAN NOT NULL DEFAULT FALSE,
   deadline TIMESTAMP WITHOUT TIME ZONE,
   owner_id VARCHAR(200) NOT NULL,
   CONSTRAINT pk_tasks PRIMARY KEY (id)
);

ALTER TABLE tasks ADD CONSTRAINT FK_TASKS_ON_USER
    FOREIGN KEY (owner_id)
    REFERENCES users (id)
    ON DELETE CASCADE;
