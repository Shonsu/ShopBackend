--liquibase formatted sql
--changeset shonsu:23
alter table users add column uuid varchar(36);
--changeset shonsu:24
update users set uuid = UUID();
