--liquibase formatted sql
--changeset shonsu:33
alter table product
    add sale_price decimal(9, 2);