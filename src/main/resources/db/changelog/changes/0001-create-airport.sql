--liquibase formatted sql

--changeset kalvis:1

CREATE TABLE airport
(
    id serial primary key,
    country    varchar not null,
    city       varchar not null,
    airport    varchar not null
);