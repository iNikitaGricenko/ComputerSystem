create TABLE authorization_log
(
    log_id               BIGINT AUTO_INCREMENT NOT NULL,
    date_time            datetime              NULL,
    authorization_status VARCHAR(255)          NULL,
    user_id              BIGINT                NULL,
    CONSTRAINT pk_authorization_log PRIMARY KEY (log_id)
);

create TABLE users
(
    user_id         BIGINT AUTO_INCREMENT NOT NULL,
    login           VARCHAR(255)          NULL,
    username        VARCHAR(255)          NULL,
    password        VARCHAR(255)          NULL,
    photo           VARCHAR(255)          NULL,
    active          BIT(1)                NULL,
    activation_code VARCHAR(255)          NULL,
    activation_url  VARCHAR(255)          NULL,
    register_date   datetime              NULL,
    last_login_date datetime              NULL,
    `role`          VARCHAR(255)          NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

alter table users
    add CONSTRAINT uc_users_login UNIQUE (login);

alter table authorization_log
    add CONSTRAINT FK_AUTHORIZATION_LOG_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);