--liquibase formatted sql
--changeset shonsu:4
alter table product add fullDescription varchar(255) after description;