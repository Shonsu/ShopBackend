--liquibase formatted sql
--changeset shonsu:34
alter table product
    add in_sale_place boolean not null;
--changeset shonsu:35
update product set in_sale_place = false;