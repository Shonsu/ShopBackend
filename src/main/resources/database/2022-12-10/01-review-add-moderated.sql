--liquibase formatted sql
--changeset shonsu:11
ALTER TABLE review
    ADD moderated BIT NOT NULL DEFAULT 0 AFTER content;