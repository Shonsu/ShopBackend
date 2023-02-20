--liquibase formatted sql
--changeset shonsu:28
alter table `order`
    add user_id bigint;
--changeset shonsu: 29
alter table `order`
    add constraint fk_order_user_id foreign key (user_id) references users (id);