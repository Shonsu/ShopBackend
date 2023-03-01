--liquibase formatted sql
--changeset shonsu:32
alter table `order`
    add order_hash varchar(12);