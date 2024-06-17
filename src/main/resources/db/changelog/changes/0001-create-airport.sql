--liquibase formatted sql

--changeset kalvis:1

CREATE TABLE AIRPORT
(
    id      serial primary key,
    airport varchar not null
)