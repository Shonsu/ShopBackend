--liquibase formatted sql
--changeset shonsu:5
alter table product modify fullDescription varchar(4048);