CREATE TABLE users (
  id VARCHAR(200) NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   created_by VARCHAR(200),
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   updated_by VARCHAR(200),
   username VARCHAR(20),
   description VARCHAR(300),
   locale VARCHAR(5),
   CONSTRAINT pk_users PRIMARY KEY (id)
);
