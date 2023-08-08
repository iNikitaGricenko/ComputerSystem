create table user_entity
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    deleted          BIT(1)                NULL,
    deleted_at   datetime              NULL,
    email        VARCHAR(255)          NOT NULL unique,
    first_name    varchar(255)         NULL,
    last_name     varchar(255)         NULL,
    online_at     timestamp(6)         NULL,
    password      varchar(255)         NOT NULL,
    registered_at datetime             NULL,
    `role`          VARCHAR(255)          NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);