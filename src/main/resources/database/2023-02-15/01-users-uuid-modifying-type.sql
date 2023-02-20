--liquibase formatted sql
--changeset shonsu:25
update users set uuid = null;
--changeset shonsu:26
alter table users modify column uuid binary(16);
--changeset shonsu:27
update users set uuid = UUID_TO_BIN(UUID());