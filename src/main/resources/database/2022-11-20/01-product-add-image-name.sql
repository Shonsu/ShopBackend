--liquibase formatted sql
--changeset shonsu:2
alter table product add image varchar(128) after currency;