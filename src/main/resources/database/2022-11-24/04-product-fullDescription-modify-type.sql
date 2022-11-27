--liquibase formatted sql
--changeset shonsu:6
alter table product modify fullDescription text default null after description;