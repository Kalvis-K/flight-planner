--liquibase formatted sql

--changeset kalvis:2

CREATE TABLE FLIGHT
(
    id      serial primary key,
    airportFrom varchar not null,
    airportTo varchar not null
)