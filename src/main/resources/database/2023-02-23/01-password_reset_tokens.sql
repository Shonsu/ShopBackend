--liquibase formatted sql
--changeset shonsu:30
create table password_reset_tokens
(
    user_id      varchar(36)  not null,
    token        varchar(128) not null unique,
    token_expiry datetime     not null,
    token_used   boolean      not null,
    PRIMARY KEY (user_id, token)
);